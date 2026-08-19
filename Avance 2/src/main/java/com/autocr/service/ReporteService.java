package com.autocr.service;

import com.autocr.domain.Pedido;
import com.autocr.domain.PedidoDetalle;
import com.autocr.repository.PedidoDetalleRepository;
import com.autocr.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Agregaciones para el panel de reportes (Avance 3). Se calculan en
 * memoria a partir de PedidoRepository/PedidoDetalleRepository: el
 * volumen de datos de un proyecto de curso (cientos de pedidos) no
 * justifica JPQL de agregacion.
 */
@Service
public class ReporteService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    public static class PuntoVenta {
        private final String etiqueta;
        private final BigDecimal total;
        private final long pedidos;

        public PuntoVenta(String etiqueta, BigDecimal total, long pedidos) {
            this.etiqueta = etiqueta;
            this.total = total;
            this.pedidos = pedidos;
        }

        public String getEtiqueta() {
            return etiqueta;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public long getPedidos() {
            return pedidos;
        }
    }

    public static class ProductoVendido {
        private final String nombre;
        private final long cantidad;
        private final BigDecimal total;

        public ProductoVendido(String nombre, long cantidad, BigDecimal total) {
            this.nombre = nombre;
            this.cantidad = cantidad;
            this.total = total;
        }

        public String getNombre() {
            return nombre;
        }

        public long getCantidad() {
            return cantidad;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }

    public static class VentaEstado {
        private final String estado;
        private final long cantidad;
        private final BigDecimal total;

        public VentaEstado(String estado, long cantidad, BigDecimal total) {
            this.estado = estado;
            this.cantidad = cantidad;
            this.total = total;
        }

        public String getEstado() {
            return estado;
        }

        public long getCantidad() {
            return cantidad;
        }

        public BigDecimal getTotal() {
            return total;
        }
    }

    @Transactional(readOnly = true)
    public List<PuntoVenta> ventasPorDia(int dias) {
        LocalDate hoy = LocalDateTime.now().toLocalDate();
        LocalDate desde = hoy.minusDays(dias - 1L);
        List<Pedido> pedidos = pedidosValidos(
                pedidoRepository.findByFechaBetweenOrderByFechaDesc(desde.atStartOfDay(), hoy.plusDays(1).atStartOfDay()));
        Map<LocalDate, List<Pedido>> porDia = pedidos.stream().collect(Collectors.groupingBy(p -> p.getFecha().toLocalDate()));

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM");
        List<PuntoVenta> resultado = new ArrayList<>();
        for (LocalDate dia = desde; !dia.isAfter(hoy); dia = dia.plusDays(1)) {
            List<Pedido> delDia = porDia.getOrDefault(dia, List.of());
            BigDecimal total = delDia.stream().map(Pedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            resultado.add(new PuntoVenta(dia.format(formato), total, delDia.size()));
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public List<PuntoVenta> ventasPorMes(int meses) {
        LocalDate hoy = LocalDateTime.now().toLocalDate();
        LocalDate desde = hoy.minusMonths(meses - 1L).withDayOfMonth(1);
        List<Pedido> pedidos = pedidosValidos(
                pedidoRepository.findByFechaBetweenOrderByFechaDesc(desde.atStartOfDay(), hoy.plusDays(1).atStartOfDay()));
        Map<String, List<Pedido>> porMes = pedidos.stream().collect(Collectors.groupingBy(p -> claveMes(p.getFecha().toLocalDate())));

        DateTimeFormatter etiqueta = DateTimeFormatter.ofPattern("MM/yyyy");
        List<PuntoVenta> resultado = new ArrayList<>();
        LocalDate cursor = desde;
        for (int i = 0; i < meses; i++) {
            List<Pedido> delMes = porMes.getOrDefault(claveMes(cursor), List.of());
            BigDecimal total = delMes.stream().map(Pedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add);
            resultado.add(new PuntoVenta(cursor.format(etiqueta), total, delMes.size()));
            cursor = cursor.plusMonths(1);
        }
        return resultado;
    }

    @Transactional(readOnly = true)
    public List<ProductoVendido> topProductos(int limite) {
        List<PedidoDetalle> detalles = pedidoDetalleRepository.findAllConProductoYPedido().stream()
                .filter(d -> !"CANCELADO".equals(d.getPedido().getEstado()))
                .toList();
        Map<String, Long> cantidadPorProducto = new HashMap<>();
        Map<String, BigDecimal> totalPorProducto = new HashMap<>();
        for (PedidoDetalle d : detalles) {
            String nombre = d.getProducto().getNombre();
            cantidadPorProducto.merge(nombre, (long) d.getCantidad(), Long::sum);
            totalPorProducto.merge(nombre, d.getSubtotal(), BigDecimal::add);
        }
        return cantidadPorProducto.entrySet().stream()
                .map(e -> new ProductoVendido(e.getKey(), e.getValue(), totalPorProducto.getOrDefault(e.getKey(), BigDecimal.ZERO)))
                .sorted(Comparator.comparingLong(ProductoVendido::getCantidad).reversed())
                .limit(limite)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<VentaEstado> ventasPorEstado() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        Map<String, List<Pedido>> porEstado = pedidos.stream().collect(Collectors.groupingBy(Pedido::getEstado));
        return porEstado.entrySet().stream()
                .map(e -> new VentaEstado(e.getKey(), e.getValue().size(),
                        e.getValue().stream().map(Pedido::getTotal).reduce(BigDecimal.ZERO, BigDecimal::add)))
                .sorted(Comparator.comparingLong(VentaEstado::getCantidad).reversed())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public BigDecimal ingresosTotales() {
        return pedidoRepository.findAll().stream()
                .filter(p -> !"CANCELADO".equals(p.getEstado()))
                .map(Pedido::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private String claveMes(LocalDate fecha) {
        return fecha.getYear() + "-" + String.format("%02d", fecha.getMonthValue());
    }

    private List<Pedido> pedidosValidos(List<Pedido> pedidos) {
        return pedidos.stream()
                .filter(p -> !"CANCELADO".equals(p.getEstado()))
                .toList();
    }
}
