package cl.varela.barberia.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.varela.barberia.model.Reserva;
@Repository
public interface ReservaRepository extends JpaRepository<Reserva , Integer>  {
    List<Reserva> findByFecha(LocalDate fecha);
    List<Reserva> findByBarbero_IdbarberoAndFechaAndHora(Integer idbarbero, LocalDate fecha, String hora);
  
    
    
    @Query(value = "SELECT COUNT(*) FROM RESERVA WHERE ID_BARBERO = :idBarbero AND FECHA = :fecha AND HORA = :hora", 
           nativeQuery = true)
    int countByBarberoFechaHora(@Param("idBarbero") Integer idBarbero, 
                               @Param("fecha") LocalDate fecha, 
                               @Param("hora") String hora);
       // Buscar por nombre de cliente
    List<Reserva> findByCliente_NombreContainingIgnoreCase(String nombre);

    // Buscar por nombre de barbero
    List<Reserva> findByBarbero_NombreContainingIgnoreCase(String nombre);

    // Buscar por ambos nombre barbero
    @Query("SELECT r FROM Reserva r WHERE " +
           "LOWER(r.cliente.nombre) LIKE LOWER(CONCAT('%', :clienteNombre, '%')) AND " +
           "LOWER(r.barbero.nombre) LIKE LOWER(CONCAT('%', :barberoNombre, '%'))")
    List<Reserva> buscarPorClienteYBarbero(@Param("clienteNombre") String clienteNombre,
                                           @Param("barberoNombre") String barberoNombre);
       // Buscar por servicio
  List<Reserva> findByServicios_NombreContainingIgnoreCase(String nombre);

      List<Reserva> findByBarbero_Idbarbero(Integer idBarbero); 

     
        @Query("SELECT SUM(r.servicios.precio) FROM Reserva r WHERE r.barbero.idbarbero = :idbarbero")
    Integer sumPreciosByBarberoId(@Param("idBarbero") Integer idbarbero);

}