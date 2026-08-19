package com.autocr.repository;

import com.autocr.domain.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DireccionRepository extends JpaRepository<Direccion, Long> {
    List<Direccion> findByUsuario_IdUsuarioOrderByPredeterminadaDescIdDireccionDesc(Long idUsuario);
}
