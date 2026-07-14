package com.autocr.carrito;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Carrito de compras temporal guardado en la sesion HTTP (H7).
 */
public class CarritoSesion implements Serializable {

    private List<ItemCarritoSesion> items = new ArrayList<>();

    public List<ItemCarritoSesion> getItems() {
        return items;
    }

    public void agregar(Long idProducto, String nombre, String imagenUrl, BigDecimal precio, int cantidad) {
        Optional<ItemCarritoSesion> existente = buscar(idProducto);
        if (existente.isPresent()) {
            existente.get().setCantidad(existente.get().getCantidad() + cantidad);
        } else {
            items.add(new ItemCarritoSesion(idProducto, nombre, imagenUrl, precio, cantidad));
        }
    }

    public void actualizarCantidad(Long idProducto, int cantidad) {
        buscar(idProducto).ifPresent(item -> item.setCantidad(cantidad));
    }

    public void eliminar(Long idProducto) {
        items.removeIf(item -> item.getIdProducto().equals(idProducto));
    }

    public void vaciar() {
        items.clear();
    }

    public boolean estaVacio() {
        return items.isEmpty();
    }

    public int cantidadTotalItems() {
        return items.stream().mapToInt(ItemCarritoSesion::getCantidad).sum();
    }

    public BigDecimal calcularTotal() {
        return items.stream()
                .map(ItemCarritoSesion::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Optional<ItemCarritoSesion> buscar(Long idProducto) {
        return items.stream().filter(i -> i.getIdProducto().equals(idProducto)).findFirst();
    }
}
