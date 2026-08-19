package com.autocr.service;

import com.autocr.domain.Categoria;
import com.autocr.repository.CategoriaRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<Categoria> listarActivas() {
        return categoriaRepository.findByActivoTrueOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public List<Categoria> listarTodas() {
        return categoriaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Categoria buscarPorId(Integer id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() -> new NegocioException("Categoria no encontrada."));
    }

    @Transactional
    public Categoria guardar(Categoria categoria) {
        if (categoria.getNombre() == null || categoria.getNombre().isBlank()) {
            throw new NegocioException("El nombre de la categoria es obligatorio.");
        }
        categoria.setNombre(categoria.getNombre().trim());
        return categoriaRepository.save(categoria);
    }

    @Transactional
    public void cambiarEstado(Integer id, boolean activo) {
        Categoria categoria = buscarPorId(id);
        categoria.setActivo(activo);
        categoriaRepository.save(categoria);
    }
}
