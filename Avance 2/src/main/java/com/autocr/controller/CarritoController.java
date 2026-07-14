package com.autocr.controller;

import com.autocr.carrito.CarritoSesion;
import com.autocr.domain.Producto;
import com.autocr.service.ProductoService;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * H7: agregar productos al carrito, actualizar cantidades, validar stock
 * y calcular subtotal. El carrito se mantiene en sesion (ver CarritoSesion)
 * para que funcione tanto para visitantes como para clientes.
 */
@Controller
public class CarritoController {

    @Autowired
    private ProductoService productoService;

    @GetMapping("/carrito")
    public String ver(HttpSession session, Model model) {
        model.addAttribute("carrito", obtenerCarrito(session));
        return "carrito/ver";
    }

    @PostMapping("/carrito/agregar")
    public String agregar(@RequestParam Long idProducto,
                           @RequestParam(defaultValue = "1") Integer cantidad,
                           HttpSession session,
                           HttpServletRequest request,
                           RedirectAttributes redirectAttributes) {

        Producto producto = productoService.buscarPorId(idProducto);
        CarritoSesion carrito = obtenerCarrito(session);

        int yaEnCarrito = carrito.getItems().stream()
                .filter(i -> i.getIdProducto().equals(idProducto))
                .mapToInt(i -> i.getCantidad())
                .findFirst().orElse(0);

        if (producto.getStock() < yaEnCarrito + cantidad) {
            redirectAttributes.addFlashAttribute("error", "No hay suficiente stock disponible de " + producto.getNombre() + ".");
        } else {
            carrito.agregar(producto.getIdProducto(), producto.getNombre(), producto.getImagenUrl(), producto.getPrecio(), cantidad);
            redirectAttributes.addFlashAttribute("mensaje", producto.getNombre() + " se agrego al carrito.");
        }

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/productos");
    }

    @PostMapping("/carrito/actualizar")
    public String actualizar(@RequestParam Long idProducto,
                              @RequestParam Integer cantidad,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        Producto producto = productoService.buscarPorId(idProducto);
        if (cantidad > producto.getStock()) {
            redirectAttributes.addFlashAttribute("error", "Solo hay " + producto.getStock() + " unidades disponibles de " + producto.getNombre() + ".");
        } else if (cantidad <= 0) {
            obtenerCarrito(session).eliminar(idProducto);
        } else {
            obtenerCarrito(session).actualizarCantidad(idProducto, cantidad);
        }
        return "redirect:/carrito";
    }

    @PostMapping("/carrito/eliminar")
    public String eliminar(@RequestParam Long idProducto, HttpSession session) {
        obtenerCarrito(session).eliminar(idProducto);
        return "redirect:/carrito";
    }

    private CarritoSesion obtenerCarrito(HttpSession session) {
        CarritoSesion carrito = (CarritoSesion) session.getAttribute(SessionKeys.CARRITO);
        if (carrito == null) {
            carrito = new CarritoSesion();
            session.setAttribute(SessionKeys.CARRITO, carrito);
        }
        return carrito;
    }
}
