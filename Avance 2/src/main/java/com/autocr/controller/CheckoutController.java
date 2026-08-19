package com.autocr.controller;

import com.autocr.carrito.CarritoSesion;
import com.autocr.carrito.DatosEntregaSesion;
import com.autocr.domain.Pedido;
import com.autocr.domain.Usuario;
import com.autocr.service.DireccionService;
import com.autocr.service.PedidoService;
import com.autocr.service.UsuarioService;
import com.autocr.util.NegocioException;
import com.autocr.util.SessionKeys;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * H8: confirmar un pedido con datos de envio, en 3 pasos (entrega, pago,
 * confirmacion), tal como se plantea en el mapa de navegacion del
 * Avance 1. Regla de negocio: solo usuarios autenticados pueden
 * confirmar pedidos (validado en cada paso mediante la sesion).
 */
@Controller
public class CheckoutController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private DireccionService direccionService;

    @GetMapping("/checkout")
    public String entregaForm(HttpSession session, Model model) {
        String validacion = validarAccesoCheckout(session);
        if (validacion != null) {
            return validacion;
        }
        model.addAttribute("carrito", obtenerCarrito(session));
        model.addAttribute("entrega", new DatosEntregaSesion());
        Long idUsuario = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
        model.addAttribute("direccionesGuardadas", direccionService.listarPorUsuario(idUsuario));
        return "checkout/entrega";
    }

    @PostMapping("/checkout/entrega")
    public String guardarEntrega(@RequestParam String nombreEntrega,
                                  @RequestParam String telefonoEntrega,
                                  @RequestParam(required = false) String provincia,
                                  @RequestParam(required = false) String canton,
                                  @RequestParam(required = false) String distrito,
                                  @RequestParam(required = false) String direccionExacta,
                                  @RequestParam(required = false) String notaEntrega,
                                  @RequestParam String metodoEntrega,
                                  HttpSession session) {
        String validacion = validarAccesoCheckout(session);
        if (validacion != null) {
            return validacion;
        }

        DatosEntregaSesion entrega = new DatosEntregaSesion();
        entrega.setNombreEntrega(nombreEntrega);
        entrega.setTelefonoEntrega(telefonoEntrega);
        entrega.setProvincia(provincia);
        entrega.setCanton(canton);
        entrega.setDistrito(distrito);
        entrega.setDireccionExacta(direccionExacta);
        entrega.setNotaEntrega(notaEntrega);
        entrega.setMetodoEntrega(metodoEntrega);
        session.setAttribute(SessionKeys.DATOS_ENTREGA, entrega);

        return "redirect:/checkout/pago";
    }

    @GetMapping("/checkout/pago")
    public String pagoForm(HttpSession session, Model model) {
        String validacion = validarAccesoCheckout(session);
        if (validacion != null) {
            return validacion;
        }
        if (session.getAttribute(SessionKeys.DATOS_ENTREGA) == null) {
            return "redirect:/checkout";
        }
        model.addAttribute("carrito", obtenerCarrito(session));
        return "checkout/pago";
    }

    @PostMapping("/checkout/confirmar")
    public String confirmar(@RequestParam String metodoPago, HttpSession session, Model model, RedirectAttributes redirectAttributes) {
        String validacion = validarAccesoCheckout(session);
        if (validacion != null) {
            return validacion;
        }

        DatosEntregaSesion entrega = (DatosEntregaSesion) session.getAttribute(SessionKeys.DATOS_ENTREGA);
        if (entrega == null) {
            return "redirect:/checkout";
        }

        try {
            Long idUsuario = (Long) session.getAttribute(SessionKeys.USUARIO_ID);
            Usuario usuario = usuarioService.buscarPorId(idUsuario);
            CarritoSesion carrito = obtenerCarrito(session);

            Pedido pedido = pedidoService.confirmarPedido(usuario, carrito, entrega, metodoPago);

            carrito.vaciar();
            session.removeAttribute(SessionKeys.DATOS_ENTREGA);

            return "redirect:/checkout/confirmacion/" + pedido.getIdPedido();
        } catch (NegocioException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/checkout/pago";
        }
    }

    @GetMapping("/checkout/confirmacion/{idPedido}")
    public String confirmacion(@PathVariable Long idPedido, Model model) {
        Pedido pedido = pedidoService.buscarPorId(idPedido);
        model.addAttribute("pedido", pedido);
        model.addAttribute("detalle", pedidoService.listarDetalle(idPedido));
        return "checkout/confirmacion";
    }

    private String validarAccesoCheckout(HttpSession session) {
        if (session.getAttribute(SessionKeys.USUARIO_ID) == null) {
            return "redirect:/login?redirect=/checkout";
        }
        CarritoSesion carrito = obtenerCarrito(session);
        if (carrito.estaVacio()) {
            return "redirect:/carrito";
        }
        return null;
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
