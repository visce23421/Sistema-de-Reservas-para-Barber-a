package cl.varela.barberia.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.varela.barberia.dto.ClienteDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.response.ResponseDTO;
import cl.varela.barberia.service.IClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Clientes", description = "Controlador de cliente")
public class ClienteController {
    @Autowired
    private IClienteService clienteService;



    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponse(responseCode = "201", description = "Cliente creado correctamente")
    @PostMapping
    public ResponseEntity<ResponseDTO> crearCliente(@RequestBody ClienteDTO clienteDTO) {
    try {
        ClienteDTO guardado = clienteService.guardar(clienteDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO("Cliente creado correctamente", List.of(guardado)));
    } catch (ConflictException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDTO("ERR-409", e.getMessage(), Map.of("error", e.getMessage())));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO("ERR-500", "Error al crear cliente", Map.of("error", e.getMessage())));
    }
}


    @Operation(summary = "Listar todos los clientes")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listar();
        return new ResponseEntity<>(clientes, HttpStatus.OK);
    }

    @Operation(summary = "Actualizar un cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizarCliente(@PathVariable Integer id, @RequestBody ClienteDTO clienteDTO) {
        ClienteDTO clienteActualizado = clienteService.actualizar(id, clienteDTO);
        if (clienteActualizado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(clienteActualizado, HttpStatus.OK);
    }
    
    @Operation(summary = "Buscar cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "409", description = "Cliente no encontrado")
    })   
     @GetMapping("/{id}")
    public ClienteDTO obtenerPorId(@PathVariable Integer id) {
        return clienteService.buscarPorId(id)
                .orElseThrow(() -> new ConflictException("Cliente no encontrado con ID: " + id));
    }

    @Operation(summary = "Eliminar cliente por ID")
    @ApiResponse(responseCode = "204", description = "Cliente eliminado correctamente")    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Integer id) {
        clienteService.eliminar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Buscar clientes por nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
        @ApiResponse(responseCode = "409", description = "No se encontraron clientes con ese nombre")
    })
    @GetMapping("/buscarPorNombre/{nombre}")
    public List<ClienteDTO> buscarPorNombre(@PathVariable String nombre) {
        List<ClienteDTO> resultado = clienteService.buscarPorNombre(nombre);
        if (resultado.isEmpty()) {
            throw new ConflictException("No se encontraron clientes con el nombre: " + nombre);
        }
        return resultado;
    }

    @Operation(summary = "Buscar clientes por teléfono")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
        @ApiResponse(responseCode = "409", description = "No se encontraron clientes con ese teléfono")
    })
    @GetMapping("/buscarPorTelefono/{telefono}")
    public List<ClienteDTO> buscarPorTelefono(@PathVariable String telefono) {
        List<ClienteDTO> resultado = clienteService.buscarPorTelefono(telefono);
        if (resultado.isEmpty()) {
            throw new ConflictException("No se encontraron clientes con el teléfono: " + telefono);
        }
        return resultado;
    }

    @Operation(summary = "Buscar clientes por correo electrónico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Clientes encontrados"),
        @ApiResponse(responseCode = "409", description = "No se encontraron clientes con ese correo")
    })
    @GetMapping("/buscarPorCorreo/{correo}")
    public List<ClienteDTO> buscarPorCorreo(@PathVariable String correo) {
        List<ClienteDTO> resultado = clienteService.buscarPorCorreo(correo);
        if (resultado.isEmpty()) {
            throw new ConflictException("No se encontraron clientes con el correo: " + correo);
        }
        return resultado;
    }

        
}
