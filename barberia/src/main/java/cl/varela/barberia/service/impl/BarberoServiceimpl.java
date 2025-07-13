package cl.varela.barberia.service.impl;

import cl.varela.barberia.dto.BarberoDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.model.Barbero;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cl.varela.barberia.repository.BarberoRepository;
import cl.varela.barberia.service.IBarberoService;
@Service
public class BarberoServiceimpl implements IBarberoService{
    @Autowired
    BarberoRepository repoBarbero;
    @Autowired
    private ModelMapper modelMapper;



    @Override
    public Barbero actualizarBarbero(Integer id, Barbero barberoActualizado) {
        return repoBarbero.findById(id).map(barberoExistente -> {
            barberoExistente.setNombre(barberoActualizado.getNombre());
            barberoExistente.setNumeroTelefonico(barberoActualizado.getNumeroTelefonico());
            return repoBarbero.save(barberoExistente);
        }).orElse(null);
    }
   @Override
    public List<BarberoDTO> listar() {
        List<Barbero> barberos = (List<Barbero>) repoBarbero.findAll();
        return barberos.stream()
                       .map(barbero -> modelMapper.map(barbero, BarberoDTO.class)) // Usar ModelMapper para convertir
                       .collect(Collectors.toList());}

    @Override
    public BarberoDTO guardar(BarberoDTO barberoDTO) {
    if (repoBarbero.existsByNumeroTelefonico(barberoDTO.getNumeroTelefonico())) {
        throw new ConflictException("Ya existe un barbero con el número telefónico: " + barberoDTO.getNumeroTelefonico());
    }

    Barbero barbero = modelMapper.map(barberoDTO, Barbero.class);
    Barbero guardado = repoBarbero.save(barbero);
    return modelMapper.map(guardado, BarberoDTO.class);
    }

    @Override
    public void eliminar(Integer id) {
        repoBarbero.deleteById(id);
    }

   @Override
    public BarberoDTO buscarPorId(Integer id) {
        Optional<Barbero> optional = repoBarbero.findById(id);
        return optional.map(barbero -> modelMapper.map(barbero, BarberoDTO.class)).orElse(null); 
    }
    @Override
    public List<Barbero> buscarPorNombre(String nombre) {
        return repoBarbero.findByNombreContainingIgnoreCase(nombre);
    }
    @Override
        public List<Barbero> buscarPorNumeroTelefonico(String numeroTelefonico) {
            return repoBarbero.findByNumeroTelefonicoContainingIgnoreCase(numeroTelefonico);
        }


}   

