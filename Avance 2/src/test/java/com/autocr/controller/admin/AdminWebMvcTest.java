package com.autocr.controller.admin;

import com.autocr.config.ProjectConfig;
import com.autocr.domain.Pedido;
import com.autocr.service.PedidoService;
import com.autocr.service.ProductoService;
import com.autocr.util.SessionKeys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({AdminDashboardController.class, AdminPedidoController.class})
@Import(ProjectConfig.class)
class AdminWebMvcTest {

    @Autowired private MockMvc mockMvc;
    @MockBean private ProductoService productoService;
    @MockBean private PedidoService pedidoService;

    @BeforeEach
    void datosBase() {
        when(productoService.listarTodosAdmin()).thenReturn(List.of());
        when(productoService.listarStockBajo()).thenReturn(List.of());
        when(pedidoService.listarTodos()).thenReturn(List.of());
    }

    @Test
    void dashboardRenderizaParaAdministrador() throws Exception {
        mockMvc.perform(get("/admin/dashboard")
                        .sessionAttr(SessionKeys.USUARIO_ROL, "ADMINISTRADOR")
                        .sessionAttr(SessionKeys.USUARIO_ID, 1L)
                        .sessionAttr(SessionKeys.USUARIO_NOMBRE, "Admin"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Resumen general")))
                .andExpect(content().string(containsString("No hay alertas de inventario")));
    }

    @Test
    void dashboardRedirigeCuandoNoHaySesion() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?redirect=/admin/dashboard"));
    }

    @Test
    void listadoDePedidosRenderizaSinResultados() throws Exception {
        mockMvc.perform(get("/admin/pedidos")
                        .sessionAttr(SessionKeys.USUARIO_ROL, "VENDEDOR"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No se encontraron pedidos")));
    }

    @Test
    void detalleDePedidoRenderiza() throws Exception {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(7L);
        pedido.setNumeroOrden("ACR-20260714-1234");
        pedido.setFecha(LocalDateTime.of(2026, 7, 14, 20, 30));
        pedido.setEstado("PENDIENTE");
        pedido.setTotal(BigDecimal.ZERO);
        pedido.setMetodoEntrega("TIENDA");
        pedido.setMetodoPago("EFECTIVO");
        when(pedidoService.buscarPorId(7L)).thenReturn(pedido);
        when(pedidoService.listarDetalle(7L)).thenReturn(List.of());
        when(pedidoService.listarHistorial(7L)).thenReturn(List.of());

        mockMvc.perform(get("/admin/pedidos/7")
                        .sessionAttr(SessionKeys.USUARIO_ROL, "INVENTARIO"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ACR-20260714-1234")))
                .andExpect(content().string(containsString("Historial de estados")));
    }
}
