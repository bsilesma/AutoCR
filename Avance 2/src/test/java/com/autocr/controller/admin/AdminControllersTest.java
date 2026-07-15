package com.autocr.controller.admin;

import com.autocr.domain.Pedido;
import com.autocr.domain.Producto;
import com.autocr.service.PedidoService;
import com.autocr.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ConcurrentModel;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminControllersTest {

    @Mock private ProductoService productoService;
    @Mock private PedidoService pedidoService;

    private AdminDashboardController dashboardController;
    private AdminPedidoController pedidoController;

    @BeforeEach
    void setUp() {
        dashboardController = new AdminDashboardController();
        pedidoController = new AdminPedidoController();
        ReflectionTestUtils.setField(dashboardController, "productoService", productoService);
        ReflectionTestUtils.setField(dashboardController, "pedidoService", pedidoService);
        ReflectionTestUtils.setField(pedidoController, "pedidoService", pedidoService);
    }

    @Test
    void dashboardCargaSusIndicadores() {
        when(productoService.listarTodosAdmin()).thenReturn(List.of(new Producto(), new Producto()));
        when(productoService.listarStockBajo()).thenReturn(List.of(new Producto()));
        when(pedidoService.listarTodos()).thenReturn(List.of(new Pedido()));
        ConcurrentModel model = new ConcurrentModel();

        assertEquals("admin/dashboard", dashboardController.dashboard(model));
        assertEquals(2, model.getAttribute("totalProductos"));
        assertEquals(1, model.getAttribute("totalPedidos"));
        assertEquals(1, ((List<?>) model.getAttribute("productosStockBajo")).size());
    }

    @Test
    void listadoSinFiltroConsultaTodosLosPedidos() {
        when(pedidoService.listarTodos()).thenReturn(List.of());

        assertEquals("admin/pedidos/listado", pedidoController.listado(null, new ConcurrentModel()));
        verify(pedidoService).listarTodos();
    }

    @Test
    void listadoConFiltroConsultaPorEstado() {
        when(pedidoService.listarPorEstado("PENDIENTE")).thenReturn(List.of());

        assertEquals("admin/pedidos/listado", pedidoController.listado("PENDIENTE", new ConcurrentModel()));
        verify(pedidoService).listarPorEstado("PENDIENTE");
    }

    @Test
    void cambioDeEstadoActualizaYRegresaAlDetalle() {
        RedirectAttributesModelMap redirect = new RedirectAttributesModelMap();

        assertEquals("redirect:/admin/pedidos/7", pedidoController.cambiarEstado(7L, "ENTREGADO", redirect));
        verify(pedidoService).cambiarEstado(7L, "ENTREGADO");
        assertEquals("Estado del pedido actualizado a ENTREGADO.", redirect.getFlashAttributes().get("mensaje"));
    }
}
