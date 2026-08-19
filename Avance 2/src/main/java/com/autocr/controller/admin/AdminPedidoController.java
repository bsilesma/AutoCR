package com.autocr.controller.admin;

import com.autocr.domain.Pedido;
import com.autocr.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * H10: consulta, detalle y actualizacion del estado de pedidos.
 */
@Controller
@RequestMapping("/admin/pedidos")
public class AdminPedidoController {

    @Autowired
    private PedidoService pedidoService;

    @GetMapping
    public String listado(@RequestParam(required = false) String estado, Model model) {
        List<Pedido> pedidos = (estado == null || estado.isBlank())
                ? pedidoService.listarTodos()
                : pedidoService.listarPorEstado(estado);
        model.addAttribute("pedidos", pedidos);
        model.addAttribute("filtroEstado", estado);
        return "admin/pedidos/listado";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        model.addAttribute("pedido", pedidoService.buscarPorId(id));
        model.addAttribute("detalle", pedidoService.listarDetalle(id));
        model.addAttribute("historial", pedidoService.listarHistorial(id));
        return "admin/pedidos/detalle";
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam String estado,
                                RedirectAttributes redirectAttributes) {
        pedidoService.cambiarEstado(id, estado);
        redirectAttributes.addFlashAttribute("mensaje", "Estado del pedido actualizado a " + estado + ".");
        return "redirect:/admin/pedidos/" + id;
    }
}
