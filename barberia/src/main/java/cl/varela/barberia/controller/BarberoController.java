package cl.varela.barberia.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.varela.barberia.dto.BarberoDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.response.ResponseDTO;
import cl.varela.barberia.service.IBarberoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

    @RestController
    @RequestMapping("/api/barberos")
@Tag(name = "Barberos", description = "Controllador de barbero")

public class BarberoController {
        @Autowired
        private IBarberoService servicio;


        @Operation(summary = "Lista todos los barberos")
        @GetMapping
        public List<BarberoDTO> listar() {
            return servicio.listar();
        }


        @Operation(summary = "busca por nombre un barbero")
        @GetMapping("/buscarPorNombre/{nombre}")
        public List<Barbero> buscarPorNombre(@PathVariable String nombre) {
        List<Barbero> resultado = servicio.buscarPorNombre(nombre);
        if (resultado.isEmpty()) {
            throw new ConflictException("No se encontraron barberos con el nombre: " + nombre);
        }
        return resultado;
        }
       

        @Operation(summary = "Crea un nuevo barbero")
        @PostMapping
        public ResponseEntity<ResponseDTO> crearBarbero(@RequestBody BarberoDTO barberoDTO) {
        try {
            BarberoDTO nuevo = servicio.guardar(barberoDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ResponseDTO("Barbero creado correctamente", List.of(nuevo)));
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ResponseDTO("ERR-409", e.getMessage(), Map.of("error", e.getMessage())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(new ResponseDTO("ERR-400", e.getMessage(), Map.of("error", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO("ERR-500", "Error al crear barbero", Map.of("error", e.getMessage())));
        }
        }

        

        @Operation(summary = "Borra un barbero por id")
        @DeleteMapping("/{id}")
        public void eliminar(@PathVariable Integer id) {
            servicio.eliminar(id);
        }

        @Operation(summary = "Buscar barbero por ID")
        @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Barbero encontrado"),
        @ApiResponse(responseCode = "409", description = "Barbero no encontrado")
            })
        @GetMapping("/{id}")
        public BarberoDTO obtenerPorId(@PathVariable Integer id) {
            BarberoDTO barbero = servicio.buscarPorId(id);
            if (barbero == null) {
                throw new ConflictException("Barbero no encontrado con ID: " + id);
            }
            return barbero;
        }

        @Operation(summary = "Actualiza el barbero por id")
        @PutMapping("/{id}")
        public ResponseEntity<Barbero> actualizarBarbero(@PathVariable Integer id, @RequestBody Barbero barbero) {
        Barbero actualizado = servicio.actualizarBarbero(id, barbero);
        if (actualizado == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
        }

        @Operation(
        summary = "Buscar barberos por número telefónico",
        description = "Permite buscar barberos cuyo número telefónico contenga el texto ingresado, ignorando mayúsculas y minúsculas."
        )
        @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Barberos encontrados correctamente"),
        @ApiResponse(responseCode = "409", description = "No se encontraron barberos con ese número telefónico")
            })
        @GetMapping("/buscarPorNumero/{numeroTelefonico}")
        public List<Barbero> buscarPorNumeroTelefonico(@PathVariable String numeroTelefonico) {
        List<Barbero> resultado = servicio.buscarPorNumeroTelefonico(numeroTelefonico);
        if (resultado.isEmpty()) {
            throw new ConflictException("No se encontraron barberos con el número telefónico: " + numeroTelefonico);
        }
        return resultado;
        }
}
