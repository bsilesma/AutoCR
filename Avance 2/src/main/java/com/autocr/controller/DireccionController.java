package com.autocr.controller;

import com.autocr.domain.Direccion;
import com.autocr.domain.Usuario;
import com.autocr.service.DireccionService;
import com.autocr.service.UsuarioService;
import com.autocr.util.NegocioException;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Direcciones guardadas del cliente (H12, Avance 3). La validacion de que
 * la direccion existe se hace en el navegador con Google Places
 * Autocomplete (ver general/fragmentos :: googleMapsScript); aqui solo se
 * persisten los datos y, si vienen, las coordenadas resueltas.
 */
@Controller
@RequestMapping("/mis-direcciones")
public class DireccionController {

    @Autowired
    private DireccionService direccionService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listado(HttpSession session, Model model) {
        Long idUsuario = idUsuarioSesion(session);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/mis-direcciones";
        }
        model.addAttribute("direcciones", direccionService.listarPorUsuario(idUsuario));
        return "direcciones/listado";
    }

    @GetMapping("/nueva")
    public String nuevaForm(HttpSession session, Model model) {
        if (idUsuarioSesion(session) == null) {
            return "redirect:/login?redirect=/mis-direcciones";
        }
        model.addAttribute("direccion", new Direccion());
        return "direcciones/formulario";
    }

    @PostMapping
    public String guardar(@RequestParam String etiqueta,
                           @RequestParam String nombreEntrega,
                           @RequestParam String telefonoEntrega,
                           @RequestParam(required = false) String provincia,
                           @RequestParam(required = false) String canton,
                           @RequestParam(required = false) String distrito,
                           @RequestParam String direccionExacta,
                           @RequestParam(required = false) String notaEntrega,
                           @RequestParam(required = false) Double latitud,
                           @RequestParam(required = false) Double longitud,
                           @RequestParam(required = false) Boolean predeterminada,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        Long idUsuario = idUsuarioSesion(session);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/mis-direcciones";
        }
        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        Direccion direccion = new Direccion();
        direccion.setEtiqueta(etiqueta);
        direccion.setNombreEntrega(nombreEntrega);
        direccion.setTelefonoEntrega(telefonoEntrega);
        direccion.setProvincia(provincia);
        direccion.setCanton(canton);
        direccion.setDistrito(distrito);
        direccion.setDireccionExacta(direccionExacta);
        direccion.setNotaEntrega(notaEntrega);
        direccion.setLatitud(latitud);
        direccion.setLongitud(longitud);
        direccion.setPredeterminada(Boolean.TRUE.equals(predeterminada));

        direccionService.guardar(usuario, direccion);
        redirectAttributes.addFlashAttribute("mensaje", "Direccion guardada.");
        return "redirect:/mis-direcciones";
    }

    @PostMapping("/{id}/predeterminada")
    public String marcarPredeterminada(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Long idUsuario = idUsuarioSesion(session);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/mis-direcciones";
        }
        try {
            direccionService.marcarPredeterminada(id, idUsuario);
        } catch (NegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mis-direcciones";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, HttpSession session, RedirectAttributes redirectAttributes) {
        Long idUsuario = idUsuarioSesion(session);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/mis-direcciones";
        }
        try {
            direccionService.eliminar(id, idUsuario);
            redirectAttributes.addFlashAttribute("mensaje", "Direccion eliminada.");
        } catch (NegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/mis-direcciones";
    }

    private Long idUsuarioSesion(HttpSession session) {
        return (Long) session.getAttribute(SessionKeys.USUARIO_ID);
    }
}
