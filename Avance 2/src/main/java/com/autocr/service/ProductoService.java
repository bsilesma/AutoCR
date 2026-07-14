package com.autocr.service;

import com.autocr.domain.Producto;
import com.autocr.repository.ProductoRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return productoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Producto> listarStockBajo() {
        return productoRepository.findByStockLessThanEqualAndActivoTrueOrderByStockAsc(STOCK_MINIMO_ALERTA);
    }

    // ---------------- H9: administracion de productos ----------------

    @Transactional
    public Producto guardar(Producto producto) {
        if (producto.getStock() != null && producto.getStock() < 0) {
            throw new NegocioException("El stock no puede ser negativo.");
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
}
