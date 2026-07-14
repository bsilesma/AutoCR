package com.autocr.controller;

import com.autocr.domain.Producto;
import com.autocr.service.CategoriaService;
import com.autocr.service.MarcaService;
import com.autocr.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Catalogo publico: listado con filtros (H2) y detalle de producto (H6).
 */
@Controller
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/productos")
    public String listado(@RequestParam(required = false) String nombre,
                           @RequestParam(required = false) Integer idMarca,
                           @RequestParam(required = false) Integer idCategoria,
                           Model model) {

        model.addAttribute("productos", productoService.buscar(nombre, idMarca, idCategoria));
        model.addAttribute("marcas", marcaService.listarActivas());
        model.addAttribute("categorias", categoriaService.listarActivas());

        // Se conservan los filtros activos en el formulario de la vista.
        model.addAttribute("filtroNombre", nombre);
        model.addAttribute("filtroMarca", idMarca);
        model.addAttribute("filtroCategoria", idCategoria);
        return "productos/listado";
    }

    @GetMapping("/productos/{id}")
    public String detalle(@PathVariable Long id, Model model) {
        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto", producto);
        return "productos/detalle";
    }
}
