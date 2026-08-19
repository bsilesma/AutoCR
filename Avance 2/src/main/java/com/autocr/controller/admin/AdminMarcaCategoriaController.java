package com.autocr.controller.admin;

import com.autocr.domain.Categoria;
import com.autocr.domain.Marca;
import com.autocr.service.CategoriaService;
import com.autocr.service.MarcaService;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Administracion de marcas y categorias (Avance 3): alta y activar/desactivar.
 * No se permite borrar: los productos existentes dependen de estas filas.
 */
@Controller
@RequestMapping("/admin/marcas-categorias")
public class AdminMarcaCategoriaController {

    @Autowired
    private MarcaService marcaService;

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    public String listado(Model model) {
        model.addAttribute("marcas", marcaService.listarTodas());
        model.addAttribute("categorias", categoriaService.listarTodas());
        return "admin/marcasCategorias/listado";
    }

    @PostMapping("/marcas")
    public String crearMarca(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        try {
            Marca marca = new Marca();
            marca.setNombre(nombre.trim());
            marcaService.guardar(marca);
            redirectAttributes.addFlashAttribute("mensaje", "Marca creada.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Ya existe una marca con ese nombre.");
        } catch (NegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/marcas-categorias";
    }

    @PostMapping("/marcas/{id}/activar")
    public String activarMarca(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        marcaService.cambiarEstado(id, true);
        redirectAttributes.addFlashAttribute("mensaje", "Marca activada.");
        return "redirect:/admin/marcas-categorias";
    }

    @PostMapping("/marcas/{id}/desactivar")
    public String desactivarMarca(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        marcaService.cambiarEstado(id, false);
        redirectAttributes.addFlashAttribute("mensaje", "Marca desactivada.");
        return "redirect:/admin/marcas-categorias";
    }

    @PostMapping("/categorias")
    public String crearCategoria(@RequestParam String nombre, RedirectAttributes redirectAttributes) {
        try {
            Categoria categoria = new Categoria();
            categoria.setNombre(nombre.trim());
            categoriaService.guardar(categoria);
            redirectAttributes.addFlashAttribute("mensaje", "Categoria creada.");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("error", "Ya existe una categoria con ese nombre.");
        } catch (NegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/admin/marcas-categorias";
    }

    @PostMapping("/categorias/{id}/activar")
    public String activarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        categoriaService.cambiarEstado(id, true);
        redirectAttributes.addFlashAttribute("mensaje", "Categoria activada.");
        return "redirect:/admin/marcas-categorias";
    }

    @PostMapping("/categorias/{id}/desactivar")
    public String desactivarCategoria(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        categoriaService.cambiarEstado(id, false);
        redirectAttributes.addFlashAttribute("mensaje", "Categoria desactivada.");
        return "redirect:/admin/marcas-categorias";
    }
}
