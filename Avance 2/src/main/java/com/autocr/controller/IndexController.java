package com.autocr.controller;

import com.autocr.service.MarcaService;
import com.autocr.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * H1: pagina de inicio con productos destacados, marcas y llamado a comprar.
 */
@Controller
public class IndexController {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private MarcaService marcaService;

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("destacados", productoService.listarDestacados());
        model.addAttribute("marcas", marcaService.listarActivas());
        return "index";
    }
}
