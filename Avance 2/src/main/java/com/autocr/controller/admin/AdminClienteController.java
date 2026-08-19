package com.autocr.controller.admin;

import com.autocr.domain.Pedido;
import com.autocr.domain.Usuario;
import com.autocr.service.PedidoService;
import com.autocr.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Vista de clientes para el vendedor (Avance 3): quienes son y cuanto han
 * comprado. Distinta de "Usuarios y Roles" (esa es administracion de
 * cuentas; esta es informacion comercial).
 */
@Controller
public class AdminClienteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PedidoService pedidoService;

    public static class FilaCliente {
        public Usuario usuario;
        public int totalPedidos;
        public BigDecimal totalComprado;
    }

    @GetMapping("/admin/clientes")
    public String listado(Model model) {
        Map<Long, List<Pedido>> pedidosPorCliente = new HashMap<>();
        for (Pedido pedido : pedidoService.listarTodos()) {
            pedidosPorCliente.computeIfAbsent(pedido.getUsuario().getIdUsuario(), id -> new ArrayList<>())
                    .add(pedido);
        }

        List<FilaCliente> filas = new ArrayList<>();
        for (Usuario usuario : usuarioService.listarTodos()) {
            if (!"CLIENTE".equals(usuario.getRol().getNombre())) {
                continue;
            }
            List<Pedido> pedidos = pedidosPorCliente.getOrDefault(usuario.getIdUsuario(), List.of());
            FilaCliente fila = new FilaCliente();
            fila.usuario = usuario;
            fila.totalPedidos = pedidos.size();
            fila.totalComprado = pedidos.stream()
                    .filter(p -> !"CANCELADO".equals(p.getEstado()))
                    .map(Pedido::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
            filas.add(fila);
        }
        filas.sort((a, b) -> b.totalComprado.compareTo(a.totalComprado));
        model.addAttribute("filas", filas);
        return "admin/clientes/listado";
    }
}
