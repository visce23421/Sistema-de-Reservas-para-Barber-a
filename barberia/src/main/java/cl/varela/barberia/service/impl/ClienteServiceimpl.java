package cl.varela.barberia.service.impl;

import cl.varela.barberia.dto.ClienteDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.model.Cliente;
import cl.varela.barberia.repository.ClienteRepository;
import cl.varela.barberia.service.IClienteService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceimpl implements IClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ModelMapper modelMapper; // Inyectar ModelMapper

    @Override
    public ClienteDTO guardar(ClienteDTO clienteDTO) {
      if (clienteRepository.existsCorreo(clienteDTO.getCorreo())) {
            throw new ConflictException("Ya existe un cliente con el correo: " + clienteDTO.getCorreo());
        }
            Cliente cliente = modelMapper.map(clienteDTO, Cliente.class); // Convertir DTO a entidad
            Cliente guardado = clienteRepository.save(cliente);
        return modelMapper.map(guardado, ClienteDTO.class); // Convertir a DTO
    }

    @Override
    public List<ClienteDTO> listar() {
        List<Cliente> clientes = (List<Cliente>) clienteRepository.findAll();
        return clientes.stream()
                       .map(cliente -> modelMapper.map(cliente, ClienteDTO.class)) // Convertir a DTO
                       .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO actualizar(Integer id, ClienteDTO clienteDTO) {
        return clienteRepository.findById(id).map(clienteExistente -> {
            clienteExistente.setNombre(clienteDTO.getNombre());
            clienteExistente.setTelefono(clienteDTO.getTelefono());
            clienteExistente.setCorreo(clienteDTO.getCorreo());
            Cliente actualizado = clienteRepository.save(clienteExistente);
            return modelMapper.map(actualizado, ClienteDTO.class); // Convertir a DTO
        }).orElse(null);
    }

    @Override
    public Optional<ClienteDTO> buscarPorId(Integer id) {
        return clienteRepository.findById(id)
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class));
    }

    @Override
    public void eliminar(Integer id) {
        clienteRepository.deleteById(id);
    }
      @Override
    public List<ClienteDTO> buscarPorNombre(String nombre) {
        return clienteRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> buscarPorTelefono(String telefono) {
        return clienteRepository.findByTelefonoContainingIgnoreCase(telefono)
                .stream()
                .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteDTO> buscarPorCorreo(String correo) {
        return clienteRepository.findByCorreoContainingIgnoreCase(correo)
            .stream()
            .map(cliente -> modelMapper.map(cliente, ClienteDTO.class))
            .collect(Collectors.toList());
}

}   
