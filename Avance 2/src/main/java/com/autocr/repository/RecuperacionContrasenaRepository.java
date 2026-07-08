package com.autocr.repository;

import com.autocr.domain.RecuperacionContrasena;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RecuperacionContrasenaRepository extends JpaRepository<RecuperacionContrasena, Long> {
    Optional<RecuperacionContrasena> findByTokenAndUsadoFalse(String token);
    Optional<RecuperacionContrasena> findTopByUsuario_IdUsuarioOrderByFechaCreacionDesc(Long idUsuario);
}
