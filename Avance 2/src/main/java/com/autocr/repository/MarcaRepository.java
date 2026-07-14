package com.autocr.repository;

import com.autocr.domain.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MarcaRepository extends JpaRepository<Marca, Integer> {
    List<Marca> findByActivoTrueOrderByNombreAsc();
}
