package cl.varela.barberia.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;

import cl.varela.barberia.dto.HorarioBarberoDTO;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.DiaSemana;
import cl.varela.barberia.model.HorarioBarbero;
import cl.varela.barberia.repository.DiaSemanaRepository;
import cl.varela.barberia.repository.HorarioBarberoRepository;
import cl.varela.barberia.service.IHorarioBarberoService;

@Service
public class HorarioBarberoServiceimpl implements IHorarioBarberoService{

    @Autowired
    private HorarioBarberoRepository repoHBR;
    @Autowired
    private ModelMapper modelMapper;

    

    public List<HorarioBarbero> obtenerHorariosPorBarbero(Integer idBarbero) {
        return repoHBR.findHorariosByBarbero(idBarbero);
    }
   
        @Override
        public HorarioBarbero crearHorario(HorarioBarbero horario) {
            if (horario.getBarbero() == null || horario.getDiaSemana() == null) {
                throw new IllegalArgumentException("Debe incluir barbero y día de semana");
            }

            boolean yaExiste = repoHBR.existsByBarberoAndDiaSemana(horario.getBarbero(), horario.getDiaSemana());

            if (yaExiste) {
                throw new IllegalArgumentException("Ya existe un horario asignado para este barbero en ese día");
            }

            return repoHBR.save(horario);
        }


   @Override
    public List<HorarioBarberoDTO> buscarPorDiaSemana(Integer idDia) {
    List<HorarioBarbero> lista = repoHBR.findByDiaSemanaIdDia(idDia);
    return lista.stream()
        .map(horario -> modelMapper.map(horario, HorarioBarberoDTO.class))
        .collect(Collectors.toList());
    }

   
    @Override
    public boolean existsByBarberoAndDiaSemana(Barbero barbero, DiaSemana diaSemana) {
        return repoHBR.existsByBarberoAndDiaSemana(barbero, diaSemana);
    }

   



}
