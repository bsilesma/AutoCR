package com.autocr.controller;

import com.autocr.service.FavoritoService;
import com.autocr.service.UsuarioService;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Lista de deseos (Avance 3): agregar/quitar productos y verlos en una
 * pagina propia.
 */
@Controller
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/favoritos")
    public String listado(HttpSession session, Model model) {
        Long idUsuario = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/favoritos";
        }
        model.addAttribute("favoritos", favoritoService.listarPorUsuario(idUsuario));
        return "favoritos/listado";
    }

    @PostMapping("/favoritos/toggle")
    public String toggle(@RequestParam Long idProducto,
                          @RequestParam(required = false) String redirect,
                          HttpSession session) {
        Long idUsuario = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        if (idUsuario == null) {
            return "redirect:/login?redirect=" + (redirect != null ? redirect : "/productos");
        }
        favoritoService.toggle(usuarioService.buscarPorId(idUsuario), idProducto);
        if (redirect != null && !redirect.isBlank()) {
            return "redirect:" + redirect;
        }
        return "redirect:/productos";
    }
}
