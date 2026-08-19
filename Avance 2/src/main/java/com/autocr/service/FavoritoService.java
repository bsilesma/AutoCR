package com.autocr.service;

import com.autocr.domain.Favorito;
import com.autocr.domain.Producto;
import com.autocr.domain.Usuario;
import com.autocr.repository.FavoritoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private ProductoService productoService;

    @Transactional(readOnly = true)
    public List<Favorito> listarPorUsuario(Long idUsuario) {
        return favoritoRepository.findByUsuario_IdUsuarioOrderByFechaAgregadoDesc(idUsuario);
    }

    // Ids para pintar el corazon en el catalogo; vacio si es un visitante sin sesion.
    @Transactional(readOnly = true)
    public Set<Long> idsFavoritosPorUsuario(Long idUsuario) {
        if (idUsuario == null) {
            return Set.of();
        }
        return favoritoRepository.findByUsuario_IdUsuarioOrderByFechaAgregadoDesc(idUsuario).stream()
                .map(f -> f.getProducto().getIdProducto())
                .collect(Collectors.toSet());
    }

    /**
     * Agrega o quita el producto de favoritos. Devuelve true si quedo
     * agregado, false si quedo quitado.
     */
    @Transactional
    public boolean toggle(Usuario usuario, Long idProducto) {
        Optional<Favorito> existente = favoritoRepository.findByUsuario_IdUsuarioAndProducto_IdProducto(usuario.getIdUsuario(), idProducto);
        if (existente.isPresent()) {
            favoritoRepository.delete(existente.get());
            return false;
        }
        Producto producto = productoService.buscarPorId(idProducto);
        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setProducto(producto);
        favoritoRepository.save(favorito);
        return true;
    }
}
