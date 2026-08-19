package com.autocr.repository;

import com.autocr.domain.Pedido;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @EntityGraph(attributePaths = "usuario")
    List<Pedido> findByUsuario_IdUsuarioOrderByFechaDesc(Long idUsuario);

    @EntityGraph(attributePaths = "usuario")
    List<Pedido> findByEstadoOrderByFechaDesc(String estado);

    List<Pedido> findByFechaBetweenOrderByFechaDesc(LocalDateTime desde, LocalDateTime hasta);

    @EntityGraph(attributePaths = "usuario")
    List<Pedido> findAllByOrderByFechaDesc();
}
