package com.autocr.service;

import com.autocr.domain.Pedido;
import com.autocr.domain.PedidoDetalle;
import com.autocr.domain.Producto;
import com.autocr.repository.PedidoDetalleRepository;
import com.autocr.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReporteServiceTest {

    @Mock private PedidoRepository pedidoRepository;
    @Mock private PedidoDetalleRepository pedidoDetalleRepository;

    private ReporteService reporteService;

    @BeforeEach
    void setUp() {
        reporteService = new ReporteService();
        ReflectionTestUtils.setField(reporteService, "pedidoRepository", pedidoRepository);
        ReflectionTestUtils.setField(reporteService, "pedidoDetalleRepository", pedidoDetalleRepository);
    }

    @Test
    void ingresosNoIncluyenPedidosCancelados() {
        when(pedidoRepository.findAll()).thenReturn(List.of(
                pedido("ENTREGADO", "25000"),
                pedido("CANCELADO", "9000"),
                pedido("PROCESANDO", "5000")
        ));

        assertEquals(new BigDecimal("30000"), reporteService.ingresosTotales());
    }

    @Test
    void topProductosNoIncluyeVentasCanceladas() {
        Producto producto = new Producto();
        producto.setNombre("Shampoo AutoCR");

        PedidoDetalle entregado = detalle(producto, pedido("ENTREGADO", "10000"), 2, "10000");
        PedidoDetalle cancelado = detalle(producto, pedido("CANCELADO", "25000"), 5, "25000");
        when(pedidoDetalleRepository.findAllConProductoYPedido()).thenReturn(List.of(entregado, cancelado));

        List<ReporteService.ProductoVendido> resultado = reporteService.topProductos(8);
        assertEquals(1, resultado.size());
        assertEquals(2, resultado.get(0).getCantidad());
        assertEquals(new BigDecimal("10000"), resultado.get(0).getTotal());
    }

    private Pedido pedido(String estado, String total) {
        Pedido pedido = new Pedido();
        pedido.setEstado(estado);
        pedido.setTotal(new BigDecimal(total));
        pedido.setFecha(LocalDateTime.now());
        return pedido;
    }

    private PedidoDetalle detalle(Producto producto, Pedido pedido, int cantidad, String subtotal) {
        PedidoDetalle detalle = new PedidoDetalle();
        detalle.setProducto(producto);
        detalle.setPedido(pedido);
        detalle.setCantidad(cantidad);
        detalle.setSubtotal(new BigDecimal(subtotal));
        return detalle;
    }
}
