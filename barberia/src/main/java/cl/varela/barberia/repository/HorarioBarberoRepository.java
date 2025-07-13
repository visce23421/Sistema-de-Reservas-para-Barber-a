package cl.varela.barberia.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cl.varela.barberia.dto.HorarioBarberoDTO;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.DiaSemana;
import cl.varela.barberia.model.HorarioBarbero;


@Repository
public interface HorarioBarberoRepository extends CrudRepository<HorarioBarbero, Integer> {

    
    @Query(value = "SELECT * FROM HORARIO_BARBERO WHERE ID_BARBERO = :idBarbero", nativeQuery = true)
List<HorarioBarbero> findHorariosByBarbero(@Param("idBarbero") Integer idBarbero);

    List<HorarioBarbero> findByDiaSemanaIdDia(Integer idDia);

    @Query("SELECT COUNT(h) > 0 FROM HorarioBarbero h WHERE h.barbero = :barbero AND h.diaSemana = :diaSemana")
boolean existsByBarberoAndDiaSemana(@Param("barbero") Barbero barbero, @Param("diaSemana") DiaSemana diaSemana);



}

