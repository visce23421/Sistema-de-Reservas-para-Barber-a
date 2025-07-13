package cl.varela.barberia.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import cl.varela.barberia.dto.ReservaDTO;
import cl.varela.barberia.model.Reserva;
import cl.varela.barberia.response.ResponseDTO;
import cl.varela.barberia.service.IReservaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/reservas")
@Tag(name = "Reserva", description = "Controlador de Reservas")
public class ReservaController {
    @Autowired
    private IReservaService servicio;

    @Operation(summary = "Buscar reservas por fecha exacta")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reservas encontradas"),
        @ApiResponse(responseCode = "404", description = "No hay reservas en esa fecha")
    })
    @GetMapping("/f/{fecha}")
    public ResponseEntity<List<Reserva>> buscarPorFecha(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        List<Reserva> reservas = servicio.obtenerPorFecha(fecha);
            return new ResponseEntity<>(reservas, HttpStatus.OK);
    }


    @Operation(summary = "Listar todas las reservas")
    @ApiResponse(responseCode = "200", description = "Listado obtenido correctamente")
    @GetMapping
    public List<Reserva> getAllReservas() {
        return servicio.findAll();
    }



    @Operation(summary = "Crear una nueva reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reserva creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error de validación de datos"),
        @ApiResponse(responseCode = "409", description = "Conflicto al crear reserva"),
        @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
   @PostMapping
    public ResponseEntity<?> crearReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO nuevaReserva = servicio.crearReserva(reservaDTO);
            return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
        
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            
            return ResponseEntity.internalServerError().body("Error al crear la reserva");
        }
    }



    @Operation(summary = "Buscar reservas por nombre de cliente")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas para el cliente")
    @GetMapping("/cliente/{nombre}")
    public ResponseEntity<List<Reserva>> buscarPorNombreCliente(@PathVariable String nombre) {
        List<Reserva> reservas = servicio.buscarPorNombreCliente(nombre);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }



    @Operation(summary = "Buscar reservas por nombre de barbero")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas para el barbero")
    @GetMapping("/barbero/{nombre}")
    public ResponseEntity<List<Reserva>> buscarPorNombreBarbero(@PathVariable String nombre) {
        List<Reserva> reservas = servicio.buscarPorNombreBarbero(nombre);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }


    @Operation(summary = "Buscar reservas por nombre de cliente y barbero")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas con los filtros")
    @GetMapping("/buscar")
    public ResponseEntity<List<Reserva>> buscarPorClienteYBarbero(
        @RequestParam String cliente, @RequestParam String barbero) {

        List<Reserva> reservas = servicio.buscarPorClienteYBarbero(cliente, barbero);
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }



    @Operation(summary = "Buscar reservas por nombre de servicio")
    @ApiResponse(responseCode = "200", description = "Reservas encontradas para el servicio")
    @GetMapping("/servicio/nombre/{nombreServicio}")
    public ResponseEntity<ResponseDTO> buscarPorNombreServicio(@PathVariable String nombreServicio) {
    List<Reserva> reservas = servicio.obtenerPorNombreServicio(nombreServicio);
    ResponseDTO response = new ResponseDTO("Consulta por nombre de servicio realizada correctamente", reservas);
    return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Actualiza el estado de una reservaa")
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoReserva(@PathVariable Integer id, @RequestParam String estado) {
    try {
        ReservaDTO actualizada = servicio.actualizarEstado(id, estado);
        return ResponseEntity.ok(new ResponseDTO("Estado actualizado correctamente", List.of(actualizada)));
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest()
            .body(new ResponseDTO("ERR-400", e.getMessage(), Map.of("error", e.getMessage())));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ResponseDTO("ERR-500", "Error al actualizar estado", Map.of("error", e.getMessage())));
    }
}
}
