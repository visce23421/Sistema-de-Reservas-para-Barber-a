package cl.varela.barberia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import cl.varela.barberia.model.Servicios;

@Repository
public interface ServiciosRepository extends JpaRepository<Servicios , Integer>  {
 boolean existsByNombreIgnoreCase(String nombre);
  @Query("SELECT s FROM Servicios s " +
           "WHERE (:nombre IS NULL OR LOWER(s.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
           "AND (:duracion IS NULL OR s.duracion = :duracion) " +
           "AND (:precio IS NULL OR s.precio = :precio)")
    List<Servicios> buscarPorFiltros(@Param("nombre") String nombre,
                                      @Param("duracion") Integer duracion,
                                      @Param("precio") Integer precio);
  boolean existsByNombre(String nombre);
}
