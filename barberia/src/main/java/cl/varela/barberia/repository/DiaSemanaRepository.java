package cl.varela.barberia.repository;

import cl.varela.barberia.model.DiaSemana;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DiaSemanaRepository extends JpaRepository<DiaSemana, Integer> {
    Optional<DiaSemana> findByNombreIgnoreCase(String nombre);
}
