package cl.varela.barberia.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import cl.varela.barberia.model.Cliente;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente , Integer> {
      // Buscar por nombre (parcial, ignoreCase)
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);

    // Buscar por teléfono (parcial, ignoreCase)
    List<Cliente> findByTelefonoContainingIgnoreCase(String telefono);

    List<Cliente> findByCorreoContainingIgnoreCase(String correo);  
    
    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cliente c WHERE UPPER(c.correo) = UPPER(:correo)")
    boolean existsCorreo(@Param("correo") String correo);



}
