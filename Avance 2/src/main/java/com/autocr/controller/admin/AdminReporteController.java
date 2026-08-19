package com.autocr.controller.admin;

import com.autocr.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Reportes y metricas de ventas (Avance 3): graficos de ventas por dia,
 * por mes, productos mas vendidos y distribucion por estado de pedido.
 */
@Controller
public class AdminReporteController {

    @Autowired
    private ReporteService reporteService;

    @GetMapping("/admin/reportes")
    public String reportes(Model model) {
        model.addAttribute("ventasPorDia", reporteService.ventasPorDia(30));
        model.addAttribute("ventasPorMes", reporteService.ventasPorMes(12));
        model.addAttribute("topProductos", reporteService.topProductos(8));
        model.addAttribute("ventasPorEstado", reporteService.ventasPorEstado());
        model.addAttribute("ingresosTotales", reporteService.ingresosTotales());
        return "admin/reportes/listado";
    }
}
