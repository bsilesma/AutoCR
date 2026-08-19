package com.autocr.service;

import com.autocr.config.PasswordUtil;
import com.autocr.domain.Rol;
import com.autocr.domain.Usuario;
import com.autocr.repository.UsuarioRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolService rolService;

    /**
     * H3: registro de un visitante como cliente.
     */
    @Transactional
    public Usuario registrarCliente(String nombre, String correo, String telefono, String contrasena) {
        if (nombre == null || nombre.isBlank() || correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            throw new NegocioException("Nombre, correo y contrasena son obligatorios.");
        }
        if (usuarioRepository.existsByCorreo(correo.trim().toLowerCase())) {
            throw new NegocioException("Ya existe una cuenta registrada con ese correo.");
        }
        Rol rolCliente = rolService.obtenerPorNombre("CLIENTE");

        Usuario usuario = new Usuario();
        usuario.setNombre(nombre.trim());
        usuario.setCorreo(correo.trim().toLowerCase());
        usuario.setTelefono(telefono);
        usuario.setContrasena(PasswordUtil.encriptar(contrasena));
        usuario.setRol(rolCliente);
        usuario.setActivo(true);
        return usuarioRepository.save(usuario);
    }

    /**
     * H4: autenticacion por correo y contrasena.
     */
    @Transactional(readOnly = true)
    public Optional<Usuario> autenticar(String correo, String contrasena) {
        if (correo == null || contrasena == null) {
            return Optional.empty();
        }
        return usuarioRepository.findByCorreo(correo.trim().toLowerCase())
                .filter(u -> Boolean.TRUE.equals(u.getActivo()))
                .filter(u -> PasswordUtil.verificar(contrasena, u.getContrasena()));
    }

    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorCorreo(String correo) {
        if (correo == null) {
            return Optional.empty();
        }
        return usuarioRepository.findByCorreo(correo.trim().toLowerCase());
    }

    @Transactional(readOnly = true)
    public Usuario buscarPorId(Long idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new NegocioException("Usuario no encontrado."));
    }

    @Transactional
    public void actualizarContrasena(Usuario usuario, String nuevaContrasena) {
        usuario.setContrasena(PasswordUtil.encriptar(nuevaContrasena));
        usuarioRepository.save(usuario);
    }

    @Transactional(readOnly = true)
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    /**
     * Inicio de sesion con Google: reutiliza la cuenta si ya existe (por
     * googleId o, si un cliente ya se habia registrado con ese correo de
     * forma local, la vincula) o crea una cuenta CLIENTE nueva.
     */
    @Transactional
    public Usuario autenticarOCrearConGoogle(String googleId, String correo, String nombre) {
        String correoNormalizado = correo.trim().toLowerCase();

        // Prioridad: primero por googleId, luego por correo, y si no existe se crea la cuenta.
        Usuario usuario = usuarioRepository.findByGoogleId(googleId)
                .or(() -> usuarioRepository.findByCorreo(correoNormalizado))
                .orElseGet(() -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setNombre(nombre != null && !nombre.isBlank() ? nombre : correoNormalizado);
                    nuevo.setCorreo(correoNormalizado);
                    nuevo.setActivo(true);
                    nuevo.setRol(rolService.obtenerPorNombre("CLIENTE"));
                    return nuevo;
                });

        if (!Boolean.TRUE.equals(usuario.getActivo())) {
            throw new NegocioException("Esta cuenta esta desactivada.");
        }

        usuario.setGoogleId(googleId);
        usuario.setProveedorAuth("GOOGLE");
        return usuarioRepository.save(usuario);
    }
}
