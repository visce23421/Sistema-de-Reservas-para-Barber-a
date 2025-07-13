package cl.varela.barberia.service;

import cl.varela.barberia.model.DiaSemana;

import java.util.List;

public interface IDiaSemanaService {
    DiaSemana obtenerPorId(Integer id);
    DiaSemana obtenerPorNombre(String nombre);
    List<DiaSemana> listarDias();
}
