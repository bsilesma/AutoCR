package com.autocr.service;

import com.autocr.domain.Direccion;
import com.autocr.domain.Usuario;
import com.autocr.repository.DireccionRepository;
import com.autocr.util.NegocioException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DireccionService {

    @Autowired
    private DireccionRepository direccionRepository;

    @Transactional(readOnly = true)
    public List<Direccion> listarPorUsuario(Long idUsuario) {
        return direccionRepository.findByUsuario_IdUsuarioOrderByPredeterminadaDescIdDireccionDesc(idUsuario);
    }

    // Valida que la direccion sea del usuario, para que nadie edite la de otro por URL.
    @Transactional(readOnly = true)
    public Direccion buscarDelUsuario(Long idDireccion, Long idUsuario) {
        Direccion direccion = direccionRepository.findById(idDireccion)
                .orElseThrow(() -> new NegocioException("Direccion no encontrada."));
        if (!direccion.getUsuario().getIdUsuario().equals(idUsuario)) {
            throw new NegocioException("Esa direccion no te pertenece.");
        }
        return direccion;
    }

    @Transactional
    public Direccion guardar(Usuario usuario, Direccion direccion) {
        direccion.setUsuario(usuario);
        if (Boolean.TRUE.equals(direccion.getPredeterminada())) {
            quitarPredeterminadaActual(usuario.getIdUsuario());
        } else if (listarPorUsuario(usuario.getIdUsuario()).isEmpty()) {
            // La primera direccion del usuario queda como predeterminada.
            direccion.setPredeterminada(true);
        }
        return direccionRepository.save(direccion);
    }

    @Transactional
    public void marcarPredeterminada(Long idDireccion, Long idUsuario) {
        quitarPredeterminadaActual(idUsuario);
        Direccion direccion = buscarDelUsuario(idDireccion, idUsuario);
        direccion.setPredeterminada(true);
        direccionRepository.save(direccion);
    }

    @Transactional
    public void eliminar(Long idDireccion, Long idUsuario) {
        Direccion direccion = buscarDelUsuario(idDireccion, idUsuario);
        direccionRepository.delete(direccion);
    }

    // Solo puede haber una predeterminada por usuario: apaga la que estuviera marcada.
    private void quitarPredeterminadaActual(Long idUsuario) {
        for (Direccion d : listarPorUsuario(idUsuario)) {
            if (Boolean.TRUE.equals(d.getPredeterminada())) {
                d.setPredeterminada(false);
                direccionRepository.save(d);
            }
        }
    }
}
