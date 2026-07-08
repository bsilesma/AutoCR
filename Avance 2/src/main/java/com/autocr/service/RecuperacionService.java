package com.autocr.service;

import com.autocr.domain.RecuperacionContrasena;
import com.autocr.domain.Usuario;
import com.autocr.repository.RecuperacionContrasenaRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

/**
 * H5: recuperacion de contrasena mediante token temporal de un solo uso.
 * Nota: el envio de correo real requiere JavaMailSender (no visto en el
 * curso), por lo que el codigo se muestra directamente en pantalla para
 * efectos de demostracion academica. Reemplazar por un envio real de
 * correo es un ajuste aislado en este servicio.
 */
@Service
public class RecuperacionService {

    private static final int MINUTOS_VALIDEZ = 15;

    @Autowired
    private RecuperacionContrasenaRepository recuperacionRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional
    public String generarToken(String correo) {
        Usuario usuario = usuarioService.buscarPorCorreo(correo)
                .orElseThrow(() -> new NegocioException("No existe una cuenta con ese correo."));

        String token = generarCodigoSeisDigitos();

        RecuperacionContrasena recuperacion = new RecuperacionContrasena();
        recuperacion.setUsuario(usuario);
        recuperacion.setToken(token);
        recuperacion.setFechaCreacion(LocalDateTime.now());
        recuperacion.setFechaExpiracion(LocalDateTime.now().plusMinutes(MINUTOS_VALIDEZ));
        recuperacion.setUsado(false);
        recuperacionRepository.save(recuperacion);

        return token;
    }

    @Transactional
    public Usuario validarTokenYObtenerUsuario(String token) {
        RecuperacionContrasena recuperacion = recuperacionRepository.findByTokenAndUsadoFalse(token)
                .orElseThrow(() -> new NegocioException("El codigo ingresado no es valido."));

        if (recuperacion.getFechaExpiracion().isBefore(LocalDateTime.now())) {
            throw new NegocioException("El codigo ha expirado, solicite uno nuevo.");
        }

        recuperacion.setUsado(true);
        recuperacionRepository.save(recuperacion);
        return recuperacion.getUsuario();
    }

    private String generarCodigoSeisDigitos() {
        SecureRandom random = new SecureRandom();
        int numero = 100000 + random.nextInt(900000);
        return String.valueOf(numero);
    }
}
