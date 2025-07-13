package cl.varela.barberia.service;
import java.util.List;

import cl.varela.barberia.dto.ServiciosDTO;
import cl.varela.barberia.model.Servicios;

public interface IServiciosService {
    ServiciosDTO buscarPorId(Integer id);
        
    List<Servicios> buscarServiciosPorFiltros(String nombre, Integer duracion, Integer precio);

    Servicios crearServicio(Servicios servicio);


}
