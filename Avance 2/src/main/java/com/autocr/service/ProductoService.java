package com.autocr.service;

import com.autocr.domain.Producto;
import com.autocr.repository.ProductoRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductoService {

    private static final int STOCK_MINIMO_ALERTA = 5;

    @Autowired
    private ProductoRepository productoRepository;

    @Transactional(readOnly = true)
    public List<Producto> listarDestacados() {
        return productoRepository.findByActivoTrueAndDestacadoTrue();
    }

    @Transactional(readOnly = true)
    public List<Producto> listarActivos() {
        return productoRepository.findByActivoTrueOrderByNombreAsc();
    }

    /**
     * H2: busqueda de productos por nombre, marca y/o categoria.
     * Cualquiera de los tres parametros puede ser nulo para no filtrar por el.
     */
    @Transactional(readOnly = true)
    public List<Producto> buscar(String nombre, Integer idMarca, Integer idCategoria) {
        String nombreFiltro = (nombre == null || nombre.isBlank()) ? null : nombre.trim();
        return productoRepository.buscar(nombreFiltro, idMarca, idCategoria);
    }

    @Transactional(readOnly = true)
    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Producto no encontrado."));
    }

    @Transactional(readOnly = true)
    public List<Producto> listarTodosAdmin() {
        return productoRepository.findAllByOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public List<Producto> listarStockBajo() {
        return productoRepository.findByStockLessThanEqualAndActivoTrueOrderByStockAsc(STOCK_MINIMO_ALERTA);
    }

    // ---------------- H9: administracion de productos ----------------

    @Transactional
    public Producto guardar(Producto producto) {
        if (producto.getNombre() == null || producto.getNombre().isBlank()) {
            throw new NegocioException("El nombre del producto es obligatorio.");
        }
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(BigDecimal.ZERO) < 0) {
            throw new NegocioException("El precio debe ser igual o mayor que cero.");
        }
        if (producto.getStock() == null || producto.getStock() < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }
        if (producto.getMarca() == null || producto.getCategoria() == null) {
            throw new NegocioException("La marca y la categoria son obligatorias.");
        }
        producto.setNombre(producto.getNombre().trim());
        producto.setDescripcion(normalizarOpcional(producto.getDescripcion()));
        producto.setImagenUrl(normalizarOpcional(producto.getImagenUrl()));
        if (producto.getActivo() == null) {
            producto.setActivo(true);
        }
        if (producto.getDestacado() == null) {
            producto.setDestacado(false);
        }
        return productoRepository.save(producto);
    }

    @Transactional
    public void cambiarEstado(Long idProducto, boolean activo) {
        Producto producto = buscarPorId(idProducto);
        producto.setActivo(activo);
        productoRepository.save(producto);
    }

    /**
     * Ajuste manual de inventario desde el panel administrativo.
     */
    @Transactional
    public void ajustarStock(Long idProducto, int nuevoStock) {
        if (nuevoStock < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
        }
        Producto producto = buscarPorId(idProducto);
        producto.setStock(nuevoStock);
        productoRepository.save(producto);
    }

    /**
     * Rebaja el stock al confirmar un pedido (H8). Regla de negocio: el
     * stock no puede quedar negativo.
     */
    @Transactional
    public void rebajarStock(Long idProducto, int cantidad) {
        Producto producto = buscarPorId(idProducto);
        int stockResultante = producto.getStock() - cantidad;
        if (stockResultante < 0) {
            throw new NegocioException("No hay suficiente stock de " + producto.getNombre() + ".");
        }
        producto.setStock(stockResultante);
        productoRepository.save(producto);
    }

    private String normalizarOpcional(String valor) {
        return valor == null || valor.isBlank() ? null : valor.trim();
    }
}
