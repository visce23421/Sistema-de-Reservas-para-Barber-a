package cl.varela.barberia.service;



import java.util.List;

import cl.varela.barberia.dto.BarberoDTO;
import cl.varela.barberia.model.Barbero;


public interface IBarberoService {
    BarberoDTO guardar(BarberoDTO barberoDTO);
    List<BarberoDTO> listar();
    Barbero actualizarBarbero(Integer id, Barbero barberod);
    void eliminar(Integer id);
    BarberoDTO buscarPorId(Integer id);
    List<Barbero> buscarPorNombre(String nombre);
    List<Barbero> buscarPorNumeroTelefonico(String numeroTelefonico);

}