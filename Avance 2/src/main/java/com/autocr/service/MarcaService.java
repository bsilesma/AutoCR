package com.autocr.service;

import com.autocr.domain.Marca;
import com.autocr.repository.MarcaRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MarcaService {

    @Autowired
    private MarcaRepository marcaRepository;

    @Transactional(readOnly = true)
    public List<Marca> listarActivas() {
        return marcaRepository.findByActivoTrueOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public List<Marca> listarTodas() {
        return marcaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Marca buscarPorId(Integer id) {
        return marcaRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Marca no encontrada."));
    }

    @Transactional
    public Marca guardar(Marca marca) {
        return marcaRepository.save(marca);
    }
}
