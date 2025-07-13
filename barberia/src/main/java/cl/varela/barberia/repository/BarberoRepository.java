package cl.varela.barberia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.varela.barberia.model.Barbero;
@Repository
public interface BarberoRepository extends JpaRepository<Barbero , Integer>  {

   List<Barbero> findByNombreContainingIgnoreCase(String nombre);
   
    List<Barbero> findByNumeroTelefonicoContainingIgnoreCase(String numeroTelefonico);
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM Barbero b WHERE b.numeroTelefonico = :numero")
    boolean existsByNumeroTelefonico(@Param("numero") String numero);

}
