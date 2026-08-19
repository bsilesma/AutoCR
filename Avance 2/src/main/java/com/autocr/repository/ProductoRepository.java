package com.autocr.repository;

import com.autocr.domain.Producto;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    @EntityGraph(attributePaths = {"marca", "categoria"})
    List<Producto> findAllByOrderByNombreAsc();

    List<Producto> findByActivoTrueAndDestacadoTrue();

    List<Producto> findByActivoTrueOrderByNombreAsc();

    // Consulta JPQL con filtros opcionales de nombre, marca y categoria (H2)
    @Query("select p from Producto p where p.activo = true "
            + "and (:nombre is null or lower(p.nombre) like lower(concat('%', :nombre, '%'))) "
            + "and (:idMarca is null or p.marca.idMarca = :idMarca) "
            + "and (:idCategoria is null or p.categoria.idCategoria = :idCategoria) "
            + "order by p.nombre asc")
    List<Producto> buscar(@Param("nombre") String nombre,
                           @Param("idMarca") Integer idMarca,
                           @Param("idCategoria") Integer idCategoria);

    List<Producto> findByStockLessThanEqualAndActivoTrueOrderByStockAsc(Integer stockMinimo);
}
