package com.autocr.service;

import com.autocr.carrito.CarritoSesion;
import com.autocr.carrito.DatosEntregaSesion;
import com.autocr.carrito.ItemCarritoSesion;
import com.autocr.domain.*;
import com.autocr.repository.PedidoDetalleRepository;
import com.autocr.repository.PedidoEstadoHistorialRepository;
import com.autocr.repository.PedidoRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoDetalleRepository pedidoDetalleRepository;

    @Autowired
    private PedidoEstadoHistorialRepository historialRepository;

    @Autowired
    private ProductoService productoService;

    /**
     * H8: confirma un pedido con los datos de envio, crea el pedido y su
     * detalle transaccional, rebaja el inventario y devuelve la orden
     * generada. Regla de negocio: solo usuarios autenticados pueden
     * confirmar pedidos (el llamador ya valida la sesion) y cada pedido
     * debe tener al menos un producto asociado.
     */
    @Transactional
    public Pedido confirmarPedido(Usuario usuario, CarritoSesion carrito, DatosEntregaSesion entrega, String metodoPago) {
        if (carrito == null || carrito.estaVacio()) {
            throw new NegocioException("El carrito esta vacio, agregue al menos un producto.");
        }

        Pedido pedido = new Pedido();
        pedido.setNumeroOrden(generarNumeroOrden());
        pedido.setUsuario(usuario);
        pedido.setFecha(LocalDateTime.now());
        pedido.setEstado("PENDIENTE");
        pedido.setMetodoEntrega(entrega.getMetodoEntrega());
        pedido.setMetodoPago(metodoPago);
        pedido.setNombreEntrega(entrega.getNombreEntrega());
        pedido.setTelefonoEntrega(entrega.getTelefonoEntrega());
        pedido.setProvincia(entrega.getProvincia());
        pedido.setCanton(entrega.getCanton());
        pedido.setDistrito(entrega.getDistrito());
        pedido.setDireccionExacta(entrega.getDireccionExacta());
        pedido.setNotaEntrega(entrega.getNotaEntrega());
        pedido.setTotal(carrito.calcularTotal());

        pedido = pedidoRepository.save(pedido);

        for (ItemCarritoSesion item : carrito.getItems()) {
            // Rebaja de inventario; lanza NegocioException si no alcanza el stock.
            productoService.rebajarStock(item.getIdProducto(), item.getCantidad());

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setPedido(pedido);
            Producto producto = productoService.buscarPorId(item.getIdProducto());
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecioUnitario(item.getPrecioUnitario());
            detalle.setSubtotal(item.getSubtotal());
            pedidoDetalleRepository.save(detalle);
        }

        registrarCambioEstado(pedido, null, "PENDIENTE");

        return pedido;
    }

    @Transactional(readOnly = true)
    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Pedido no encontrado."));
    }

    @Transactional(readOnly = true)
    public List<PedidoDetalle> listarDetalle(Long idPedido) {
        return pedidoDetalleRepository.findByPedido_IdPedido(idPedido);
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorUsuario(Long idUsuario) {
        return pedidoRepository.findByUsuario_IdUsuarioOrderByFechaDesc(idUsuario);
    }

    // ---------------- H10: administracion de pedidos ----------------

    @Transactional(readOnly = true)
    public List<Pedido> listarTodos() {
        return pedidoRepository.findAllByOrderByFechaDesc();
    }

    @Transactional(readOnly = true)
    public List<Pedido> listarPorEstado(String estado) {
        return pedidoRepository.findByEstadoOrderByFechaDesc(estado);
    }

    @Transactional
    public void cambiarEstado(Long idPedido, String nuevoEstado) {
        Pedido pedido = buscarPorId(idPedido);
        String anterior = pedido.getEstado();
        pedido.setEstado(nuevoEstado);
        pedidoRepository.save(pedido);
        registrarCambioEstado(pedido, anterior, nuevoEstado);
    }

    @Transactional(readOnly = true)
    public List<PedidoEstadoHistorial> listarHistorial(Long idPedido) {
        return historialRepository.findByPedido_IdPedidoOrderByFechaAsc(idPedido);
    }

    private void registrarCambioEstado(Pedido pedido, String anterior, String nuevo) {
        PedidoEstadoHistorial historial = new PedidoEstadoHistorial();
        historial.setPedido(pedido);
        historial.setEstadoAnterior(anterior);
        historial.setEstadoNuevo(nuevo);
        historial.setFecha(LocalDateTime.now());
        historialRepository.save(historial);
    }

    private String generarNumeroOrden() {
        // Numero de orden legible para el cliente: ACR-yyyyMMdd-<4 digitos al azar>
        String hoy = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String aleatorio = String.valueOf((int) (Math.random() * 9000) + 1000);
        return "ACR-" + hoy + "-" + aleatorio;
    }
}
