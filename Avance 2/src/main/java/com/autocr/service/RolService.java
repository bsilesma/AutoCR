package com.autocr.service;

import com.autocr.domain.Rol;
import com.autocr.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Rol obtenerPorNombre(String nombre) {
        return rolRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("El rol " + nombre + " no existe. Verifique creaTablas.sql"));
    }
}
