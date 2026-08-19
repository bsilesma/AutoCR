package com.autocr.controller.admin;

import com.autocr.service.ProductoService;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Comparator;
import java.util.List;

/**
 * Pagina dedicada de inventario (Avance 3): todos los productos ordenados
 * por stock ascendente, con ajuste rapido de cantidad. La alerta de stock
 * bajo ya vivia en el dashboard desde el Avance 2; esta vista la completa
 * con la gestion real del inventario.
 */
@Controller
@RequestMapping("/admin/inventario")
public class AdminInventarioController {

    @Autowired
    private ProductoService productoService;

    @GetMapping
    public String listado(Model model) {
        List<com.autocr.domain.Producto> productos = productoService.listarTodosAdmin();
        productos.sort(Comparator.comparing(com.autocr.domain.Producto::getStock));
        model.addAttribute("productos", productos);
        return "admin/inventario/listado";
    }

    @PostMapping("/{id}/ajustar")
    public String ajustar(@PathVariable Long id, @RequestParam Integer nuevoStock, RedirectAttributes redirectAttributes) {
        try {
            if (nuevoStock == null) {
                throw new NegocioException("Debe indicar el nuevo stock.");
            }
            productoService.ajustarStock(id, nuevoStock);
            redirectAttributes.addFlashAttribute("mensaje", "Stock actualizado.");
        } catch (NegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/inventario";
    }
}
