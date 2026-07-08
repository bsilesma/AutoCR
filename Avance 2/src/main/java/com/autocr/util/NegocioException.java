package com.autocr.util;

/**
 * Excepcion utilizada para comunicar violaciones de reglas de negocio
 * (correo duplicado, stock insuficiente, etc.) desde la capa de servicio
 * hacia los controladores, que la traducen en mensajes para el usuario.
 */
public class NegocioException extends RuntimeException {
    public NegocioException(String mensaje) {
        super(mensaje);
    }
}
