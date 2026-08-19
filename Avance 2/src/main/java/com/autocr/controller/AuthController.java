package com.autocr.controller;

import com.autocr.domain.Usuario;
import com.autocr.service.RecuperacionService;
import com.autocr.service.UsuarioService;
import com.autocr.util.NegocioException;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

/**
 * Controlador de autenticacion y cuenta: registro (H3), login (H4) y
 * recuperacion de contrasena (H5).
 */
@Controller
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RecuperacionService recuperacionService;

    // ---------------- Login (H4) ----------------

    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "redirect", required = false) String redirect,
                             @RequestParam(value = "googleNoConfigurado", required = false) Boolean googleNoConfigurado,
                             Model model) {
        model.addAttribute("redirect", redirect);
        if (Boolean.TRUE.equals(googleNoConfigurado)) {
            model.addAttribute("error", "El inicio de sesion con Google todavia no esta configurado.");
        }
        return "auth/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String correo,
                         @RequestParam String contrasena,
                         @RequestParam(value = "redirect", required = false) String redirect,
                         HttpSession session,
                         Model model) {

        Optional<Usuario> usuarioOpt = usuarioService.autenticar(correo, contrasena);
        if (usuarioOpt.isEmpty()) {
            model.addAttribute("error", "Correo o contrasena incorrectos.");
            model.addAttribute("redirect", redirect);
            return "auth/login";
        }

        Usuario usuario = usuarioOpt.get();
        iniciarSesion(session, usuario);

        String rol = usuario.getRol().getNombre();
        if (redirect != null && !redirect.isBlank()) {
            return "redirect:" + redirect;
        }
        if ("ADMINISTRADOR".equals(rol) || "VENDEDOR".equals(rol) || "INVENTARIO".equals(rol)) {
            return "redirect:/admin/dashboard";
        }
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // ---------------- Registro (H3) ----------------

    @GetMapping("/registro")
    public String registroForm() {
        return "auth/registro";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre,
                            @RequestParam String correo,
                            @RequestParam(required = false) String telefono,
                            @RequestParam String contrasena,
                            HttpSession session,
                            Model model) {
        try {
            Usuario usuario = usuarioService.registrarCliente(nombre, correo, telefono, contrasena);
            iniciarSesion(session, usuario);
            return "redirect:/";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("nombre", nombre);
            model.addAttribute("correo", correo);
            model.addAttribute("telefono", telefono);
            return "auth/registro";
        }
    }

    // ---------------- Recuperacion de contrasena (H5) ----------------

    @GetMapping("/recuperar")
    public String recuperarForm() {
        return "auth/recuperar";
    }

    @PostMapping("/recuperar/enviar")
    public String enviarCodigo(@RequestParam String correo, Model model) {
        try {
            String token = recuperacionService.generarToken(correo);
            model.addAttribute("correo", correo);
            // Simulacion de envio de correo: se muestra el codigo en pantalla.
            model.addAttribute("codigoSimulado", token);
            return "auth/recuperarNueva";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/recuperar";
        }
    }

    @PostMapping("/recuperar/confirmar")
    public String confirmarNuevaClave(@RequestParam String token,
                                       @RequestParam String nuevaContrasena,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        try {
            Usuario usuario = recuperacionService.validarTokenYObtenerUsuario(token);
            usuarioService.actualizarContrasena(usuario, nuevaContrasena);
            redirectAttributes.addFlashAttribute("mensaje", "Contrasena actualizada, ya puede iniciar sesion.");
            return "redirect:/login";
        } catch (NegocioException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/recuperarNueva";
        }
    }

    private void iniciarSesion(HttpSession session, Usuario usuario) {
        session.setAttribute(SessionKeys.USUARIO_ID, usuario.getIdUsuario());
        session.setAttribute(SessionKeys.USUARIO_NOMBRE, usuario.getNombre());
        session.setAttribute(SessionKeys.USUARIO_ROL, usuario.getRol().getNombre());
    }
}
