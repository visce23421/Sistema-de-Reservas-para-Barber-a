package cl.varela.barberia.service.impl;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  cl.varela.barberia.model.Servicios;
import cl.varela.barberia.dto.ServiciosDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.repository.ServiciosRepository;
import cl.varela.barberia.service.IServiciosService;




@Service
public class ServiciosServiceimpl implements IServiciosService{
    @Autowired
    private ServiciosRepository serRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ServiciosDTO buscarPorId(Integer id) {
        Optional<Servicios> optional = serRepository.findById(id);
        return optional.map(service -> modelMapper.map(service, ServiciosDTO.class)).orElse(null);
    }
    @Override
    public List<Servicios> buscarServiciosPorFiltros(String nombre, Integer duracion, Integer precio) {

        List<Servicios> resultados = serRepository.buscarPorFiltros(nombre, duracion, precio);

        if (resultados.isEmpty()) {
            throw new ConflictException("No se encontraron servicios con los filtros proporcionados");
        }

        return resultados;
    }
    @Override
    public Servicios crearServicio(Servicios nuevoServicio) {
  

        return serRepository.save(nuevoServicio);
    }
}


