package com.autocr.service;

import com.autocr.domain.Rol;
import com.autocr.repository.RolRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new NegocioException(
                        "El rol " + nombre + " no existe. Verifique database/autocr.sql"));
    }

    public List<Rol> listarTodos() {
        return rolRepository.findAll();
    }
}
