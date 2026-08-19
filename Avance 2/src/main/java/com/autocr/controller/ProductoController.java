package com.autocr.controller;

import com.autocr.domain.Producto;
import com.autocr.service.CategoriaService;
import com.autocr.service.FavoritoService;
import com.autocr.service.MarcaService;
import com.autocr.service.ProductoService;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
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

    @Autowired
    private FavoritoService favoritoService;

    @GetMapping("/productos")
    public String listado(@RequestParam(required = false) String nombre,
                           @RequestParam(required = false) Integer idMarca,
                           @RequestParam(required = false) Integer idCategoria,
                           HttpSession session,
                           Model model) {

        model.addAttribute("productos", productoService.buscar(nombre, idMarca, idCategoria));
        model.addAttribute("marcas", marcaService.listarActivas());
        model.addAttribute("categorias", categoriaService.listarActivas());
        // Ids de favoritos del usuario en sesion, para marcar el corazon de cada tarjeta.
        model.addAttribute("idsFavoritos", favoritoService.idsFavoritosPorUsuario((Long) session.getAttribute(SessionKeys.USUARIO_ID)));

        // Se conservan los filtros activos en el formulario de la vista.
        model.addAttribute("filtroNombre", nombre);
        model.addAttribute("filtroMarca", idMarca);
        model.addAttribute("filtroCategoria", idCategoria);
        return "productos/listado";
    }

    @GetMapping("/productos/{id}")
    public String detalle(@PathVariable Long id, HttpSession session, Model model) {
        Producto producto = productoService.buscarPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("idsFavoritos", favoritoService.idsFavoritosPorUsuario((Long) session.getAttribute(SessionKeys.USUARIO_ID)));
        return "productos/detalle";
    }
}
