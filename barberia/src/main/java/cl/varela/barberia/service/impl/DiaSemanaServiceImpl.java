package cl.varela.barberia.service.impl;

import cl.varela.barberia.model.DiaSemana;
import cl.varela.barberia.repository.DiaSemanaRepository;
import cl.varela.barberia.service.IDiaSemanaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiaSemanaServiceImpl implements IDiaSemanaService {

    @Autowired
    private DiaSemanaRepository repo;

    @Override
    public DiaSemana obtenerPorId(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Día con ID " + id + " no encontrado"));
    }

    @Override
    public DiaSemana obtenerPorNombre(String nombre) {
        return repo.findByNombreIgnoreCase(nombre)
            .orElseThrow(() -> new IllegalArgumentException("Día con nombre " + nombre + " no encontrado"));
    }

    @Override
    public List<DiaSemana> listarDias() {
        return repo.findAll();
    }
}
