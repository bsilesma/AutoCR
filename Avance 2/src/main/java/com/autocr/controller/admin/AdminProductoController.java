package com.autocr.controller.admin;

import com.autocr.domain.Producto;
import com.autocr.service.CategoriaService;
import com.autocr.service.MarcaService;
import com.autocr.service.ProductoService;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

/** Gestion de productos desde el panel administrativo. */
@Controller
@RequestMapping("/admin/productos")
public class AdminProductoController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listado(Model model) {
        List<Producto> productos = productoService.listarTodosAdmin();
        productos.sort(Comparator.comparing(Producto::getNombre, String.CASE_INSENSITIVE_ORDER));
        model.addAttribute("productos", productos);
        return "admin/productos/listado";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        cargarFormulario(model, new Producto());
        return "admin/productos/formulario";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        cargarFormulario(model, productoService.buscarPorId(id));
        return "admin/productos/formulario";
    }

    @PostMapping("/guardar")
    public String guardar(@RequestParam(required = false) Long idProducto,
                          @RequestParam String nombre,
                          @RequestParam(required = false) String descripcion,
                          @RequestParam BigDecimal precio,
                          @RequestParam Integer stock,
                          @RequestParam(required = false) String imagenUrl,
                          @RequestParam(defaultValue = "false") boolean destacado,
                          @RequestParam Integer idMarca,
                          @RequestParam Integer idCategoria,
                          RedirectAttributes redirectAttributes) {
        try {
            Producto producto = idProducto == null ? new Producto() : productoService.buscarPorId(idProducto);
            producto.setNombre(nombre);
            producto.setDescripcion(descripcion);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setImagenUrl(imagenUrl);
            producto.setDestacado(destacado);
            producto.setMarca(marcaService.buscarPorId(idMarca));
            producto.setCategoria(categoriaService.buscarPorId(idCategoria));
            productoService.guardar(producto);
            redirectAttributes.addFlashAttribute("mensaje", "Producto guardado correctamente.");
            return "redirect:/admin/productos";
        } catch (NegocioException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return idProducto == null
                    ? "redirect:/admin/productos/nuevo"
                    : "redirect:/admin/productos/" + idProducto + "/editar";
        }
    }

    @PostMapping("/{id}/estado")
    public String cambiarEstado(@PathVariable Long id,
                                @RequestParam boolean activo,
                                RedirectAttributes redirectAttributes) {
        productoService.cambiarEstado(id, activo);
        redirectAttributes.addFlashAttribute("mensaje", activo ? "Producto activado." : "Producto desactivado.");
        return "redirect:/admin/productos";
    }

    private void cargarFormulario(Model model, Producto producto) {
        model.addAttribute("producto", producto);
        model.addAttribute("marcas", marcaService.listarTodas());
        model.addAttribute("categorias", categoriaService.listarTodas());
    }
}
