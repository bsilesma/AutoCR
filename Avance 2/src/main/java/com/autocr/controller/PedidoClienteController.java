package com.autocr.controller;

import com.autocr.domain.Pedido;
import com.autocr.service.PedidoService;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Historial de pedidos del cliente (H11, Avance 3): lista y detalle de sus
 * propias compras, reutilizando PedidoService.listarPorUsuario (ya existia
 * como gancho sin usar desde el Avance 2).
 */
@Controller
public class PedidoClienteController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/mis-pedidos")
    public String misPedidos(HttpSession session, Model model) {
        Long idUsuario = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/mis-pedidos";
        }
        model.addAttribute("pedidos", pedidoService.listarPorUsuario(idUsuario));
        return "pedidos/misPedidos";
    }

    @GetMapping("/mis-pedidos/{id}")
    public String detalle(@PathVariable Long id, HttpSession session, Model model) {
        Long idUsuario = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        if (idUsuario == null) {
            return "redirect:/login?redirect=/mis-pedidos";
        }
        Pedido pedido = pedidoService.buscarPorId(id);
        // Un cliente solo ve sus propios pedidos; si no es suyo vuelve al listado.
        if (!pedido.getUsuario().getIdUsuario().equals(idUsuario)) {
            return "redirect:/mis-pedidos";
        }
        model.addAttribute("pedido", pedido);
        model.addAttribute("detalle", pedidoService.listarDetalle(id));
        model.addAttribute("historial", pedidoService.listarHistorial(id));
        return "pedidos/miPedidoDetalle";
    }
}
