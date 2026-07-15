package com.autocr.controller.admin;

import com.autocr.service.PedidoService;
import com.autocr.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Pantalla principal del panel administrativo.
 */
@Controller
public class AdminDashboardController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalProductos", productoService.listarTodosAdmin().size());
        model.addAttribute("productosStockBajo", productoService.listarStockBajo());
        model.addAttribute("totalPedidos", pedidoService.listarTodos().size());
        return "admin/dashboard";
    }
}
