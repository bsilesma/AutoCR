package com.autocr.controller.admin;

import com.autocr.service.RolService;
import com.autocr.service.UsuarioService;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Administracion de usuarios y roles (Avance 3, historia de baja
 * prioridad). Solo ADMINISTRADOR puede cambiar roles o desactivar
 * cuentas; VENDEDOR/INVENTARIO no ven esta seccion aunque el
 * interceptor los deje entrar al resto del panel.
 */
@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @GetMapping
    public String listado(HttpSession session, Model model) {
        String validacion = soloAdministrador(session);
        if (validacion != null) {
            return validacion;
        }
        model.addAttribute("usuarios", usuarioService.listarTodos());
        model.addAttribute("roles", rolService.listarTodos());
        return "admin/usuarios/listado";
    }

    @PostMapping("/{id}/rol")
    public String cambiarRol(@PathVariable Long id, @RequestParam String nombreRol,
                              HttpSession session, RedirectAttributes redirectAttributes) {
        String validacion = soloAdministrador(session);
        if (validacion != null) {
            return validacion;
        }
        Long idPropio = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        if (id.equals(idPropio) && !"ADMINISTRADOR".equals(nombreRol)) {
            redirectAttributes.addFlashAttribute("error", "No podes quitarte tu propio rol de administrador.");
            return "redirect:/admin/usuarios";
        }
        usuarioService.cambiarRol(id, nombreRol);
        redirectAttributes.addFlashAttribute("mensaje", "Rol actualizado.");
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/activar")
    public String activar(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String validacion = soloAdministrador(session);
        if (validacion != null) {
            return validacion;
        }
        usuarioService.cambiarEstado(id, true);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario activado.");
        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/desactivar")
    public String desactivar(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        String validacion = soloAdministrador(session);
        if (validacion != null) {
            return validacion;
        }
        Long idPropio = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        if (id.equals(idPropio)) {
            redirectAttributes.addFlashAttribute("error", "No te podes desactivar a vos mismo.");
            return "redirect:/admin/usuarios";
        }
        usuarioService.cambiarEstado(id, false);
        redirectAttributes.addFlashAttribute("mensaje", "Usuario desactivado.");
        return "redirect:/admin/usuarios";
    }

    private String soloAdministrador(HttpSession session) {
        if (!"ADMINISTRADOR".equals(session.getAttribute(SessionKeys.USUARIO_ROL))) {
            return "redirect:/admin/dashboard";
        }
        return null;
    }
}
