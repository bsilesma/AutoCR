package com.autocr.repository;

import com.autocr.domain.PedidoEstadoHistorial;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoEstadoHistorialRepository extends JpaRepository<PedidoEstadoHistorial, Long> {
    List<PedidoEstadoHistorial> findByPedido_IdPedidoOrderByFechaAsc(Long idPedido);
}
