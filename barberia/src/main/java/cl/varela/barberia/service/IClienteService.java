package cl.varela.barberia.service;

import java.util.List;
import java.util.Optional;

import cl.varela.barberia.dto.ClienteDTO;
import cl.varela.barberia.model.Cliente;

public interface IClienteService {
    ClienteDTO guardar(ClienteDTO clienteDTO);
    List<ClienteDTO> listar();
    ClienteDTO actualizar(Integer id, ClienteDTO clienteDTO);
    Optional<ClienteDTO> buscarPorId(Integer id);
    void eliminar(Integer id);
    List<ClienteDTO> buscarPorNombre(String nombre);

    List<ClienteDTO> buscarPorTelefono(String telefono);
    List<ClienteDTO> buscarPorCorreo(String correo);


}
