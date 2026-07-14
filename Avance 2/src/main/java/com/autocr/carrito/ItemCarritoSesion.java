package com.autocr.carrito;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Representa una linea del carrito mientras el usuario todavia no ha
 * confirmado el pedido. Vive dentro de la sesion HTTP (atributo
 * SessionKeys.CARRITO) para permitir su uso incluso como visitante,
 * segun el mapa de navegacion del Avance 1: Detalle -> Carrito ->
 * Login/Registro -> Checkout.
 */
public class ItemCarritoSesion implements Serializable {

    private Long idProducto;
    private String nombreProducto;
    private String imagenUrl;
    private BigDecimal precioUnitario;
    private Integer cantidad;

    public ItemCarritoSesion() {
    }

    public ItemCarritoSesion(Long idProducto, String nombreProducto, String imagenUrl, BigDecimal precioUnitario, Integer cantidad) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.imagenUrl = imagenUrl;
        this.precioUnitario = precioUnitario;
        this.cantidad = cantidad;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
