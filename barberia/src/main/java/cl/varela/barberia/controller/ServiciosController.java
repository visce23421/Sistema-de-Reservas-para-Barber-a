package cl.varela.barberia.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.varela.barberia.dto.ServiciosDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.model.Servicios;
import cl.varela.barberia.response.ResponseDTO;
import cl.varela.barberia.service.IServiciosService;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/servicios")
@Tag(name = "Servicios", description = "Controlador de Servicios")
public class ServiciosController {
    @Autowired
    private IServiciosService serviciosService;


    
    @GetMapping("/{id}")
    public ResponseEntity<ServiciosDTO> buscarServicioPorId(@PathVariable Integer id) {
        ServiciosDTO servicio = serviciosService.buscarPorId(id);
        if (servicio == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(servicio, HttpStatus.OK);
    }


    @GetMapping("/buscar")
    public ResponseEntity<ResponseDTO>  buscarServicios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer duracion,
            @RequestParam(required = false) Integer precio) {

       if (nombre == null && duracion == null && precio == null) {
        throw new ConflictException("Debe dar por lo menos un parametro (nombre, duración o precio)");
    }

    List<Servicios> resultados = serviciosService.buscarServiciosPorFiltros(nombre, duracion, precio);
    
    if (resultados.isEmpty()) {
        throw new ConflictException("No se encontraron servicios ");
    }
    return new ResponseEntity<>(new ResponseDTO("Servicios encontrados", resultados), HttpStatus.OK);
        
    }


    @PostMapping
    public ResponseEntity<ResponseDTO> crearServicio(@RequestBody Servicios servicio) {
        try {
            Servicios creado = serviciosService.crearServicio(servicio);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO("Servicio creado exitosamente", List.of(creado)));
        } catch (ConflictException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ResponseDTO("ERR-409", e.getMessage(), Map.of("error", e.getMessage())));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new ResponseDTO("ERR-400", e.getMessage(), Map.of("error", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO("ERR-500", "Error interno al crear servicio", Map.of("error", e.getMessage())));
        }
    }
}


