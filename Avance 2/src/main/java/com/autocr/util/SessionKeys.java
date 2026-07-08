package com.autocr.util;

/**
 * Nombres de los atributos guardados en la sesion HTTP luego de iniciar
 * sesion. Se centralizan aqui para que todos los controladores e
 * interceptores usen exactamente las mismas llaves.
 */
public class SessionKeys {
    public static final String USUARIO_ID = "usuarioId";
    public static final String USUARIO_NOMBRE = "usuarioNombre";
    public static final String USUARIO_ROL = "usuarioRol";
    public static final String CARRITO = "carritoSesion";
    public static final String DATOS_ENTREGA = "datosEntregaSesion";

    private SessionKeys() {
    }
}
