package cl.varela.barberia.repository;

import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.ReporteIngresosBarbero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ReporteIngresosBarberoRepository extends JpaRepository<ReporteIngresosBarbero, Integer> {
    Optional<ReporteIngresosBarbero> findByBarbero(Barbero barbero);
     Optional<ReporteIngresosBarbero> findByBarberoAndFecha(Barbero barbero, LocalDate fecha);
}
