package cl.varela.barberia.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import cl.varela.barberia.dto.HorarioBarberoDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.DiaSemana;
import cl.varela.barberia.model.HorarioBarbero;
import cl.varela.barberia.repository.BarberoRepository;
import cl.varela.barberia.repository.DiaSemanaRepository;
import cl.varela.barberia.response.ResponseDTO;
import cl.varela.barberia.service.IDiaSemanaService;
import cl.varela.barberia.service.IHorarioBarberoService;
import cl.varela.barberia.service.impl.DiaSemanaServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/horarios")
@Tag(name = "Horarios De los Barberos", description = "Controlador de Horarios")
public class HorarioBarberoController {
    @Autowired
    private IHorarioBarberoService HBService;

    @Autowired
    private IDiaSemanaService dss;

    @Autowired
    private BarberoRepository barberoRepository;

    @Operation(summary = "Listar horarios de un barbero por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Horarios encontrados"),
        @ApiResponse(responseCode = "404", description = "Barbero no encontrado")
    })
    @GetMapping("/barbero/{idBarbero}")
    public ResponseEntity<List<HorarioBarbero>> getHorariosByBarberoId(@PathVariable Integer idBarbero) {
        List<HorarioBarbero> horarios = HBService.obtenerHorariosPorBarbero(idBarbero);
        return ResponseEntity.ok(horarios);
    }


    @Operation(summary = "Buscar horarios por día de la semana")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Horarios encontrados"),
        @ApiResponse(responseCode = "409", description = "No hay horarios para ese día")
    })
    @GetMapping("/buscarPorDia/{idDia}")
    public List<HorarioBarberoDTO> buscarPorDiaSemana(@PathVariable Integer idDia) {
        List<HorarioBarberoDTO> resultado = HBService.buscarPorDiaSemana(idDia);
        if (resultado.isEmpty()) {
            throw new ConflictException("No hay horarios para el día con ID: " + idDia);
        }
        return resultado;
    }



    @Operation(summary = "crea un nuevo horario")
    @PostMapping
    public ResponseEntity<ResponseDTO> crearHorario(@RequestBody HorarioBarberoDTO horarioDTO) {
        try {
            // Primero recuperamos el Barbero desde el id
            Integer idBarbero = horarioDTO.getBarbero().getIdbarbero();
            Barbero barbero = barberoRepository.findById(idBarbero)
                .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado"));

             Integer idDia= horarioDTO.getDiaSemana().getIdDia();
             DiaSemana diaSemana = dss.obtenerPorId(idDia);

              boolean yaExiste = HBService.existsByBarberoAndDiaSemana(barbero, diaSemana);
            if (yaExiste) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ResponseDTO("ERR-409", "Ya existe un horario asignado a este barbero en ese día",
                        Map.of("idBarbero", idBarbero, "idDia", idDia))
                );
        }
            
            HorarioBarbero horario = new HorarioBarbero();
            horario.setBarbero(barbero);
            horario.setDiaSemana(diaSemana);
            horario.setHoraInicio(horarioDTO.getHoraInicio());
            horario.setHoraFin(horarioDTO.getHoraFin());

            HorarioBarbero creado = HBService.crearHorario(horario);
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDTO("Horario de barbero creado exitosamente", List.of(creado)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                .body(new ResponseDTO("ERR-400", e.getMessage(), Map.of("error", e.getMessage())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ResponseDTO("ERR-500", "Error interno al crear horario", Map.of("error", e.getMessage())));
        }
        }
    }


