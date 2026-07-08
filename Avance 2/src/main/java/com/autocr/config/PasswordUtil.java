package com.autocr.config;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utilitario simple para cifrar contrasenas con SHA-256.
 * No se utiliza Spring Security (no visto en el curso); se resuelve con
 * una utilidad propia en Java puro, tal como se maneja el resto de la
 * logica de negocio del proyecto (capa de servicio).
 */
public class PasswordUtil {

    private static final String SALT = "autocr-sc403-salt";

    private PasswordUtil() {
    }

    public static String encriptar(String textoPlano) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest((SALT + textoPlano).getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("No fue posible cifrar la contrasena", e);
        }
    }

    public static boolean verificar(String textoPlano, String hashGuardado) {
        return encriptar(textoPlano).equals(hashGuardado);
    }
}
