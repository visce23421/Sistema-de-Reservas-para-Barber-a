package cl.varela.barberia.service;

import java.util.List;

import cl.varela.barberia.dto.HorarioBarberoDTO;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.DiaSemana;
import cl.varela.barberia.model.HorarioBarbero;

public interface IHorarioBarberoService {
  

    List<HorarioBarbero> obtenerHorariosPorBarbero(Integer idbarbero);
    
    HorarioBarbero crearHorario(HorarioBarbero horario);
    List<HorarioBarberoDTO> buscarPorDiaSemana(Integer idDia);
    boolean existsByBarberoAndDiaSemana(Barbero barbero, DiaSemana diaSemana);


}
