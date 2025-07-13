package cl.varela.barberia.controller;

import cl.varela.barberia.dto.ReporteIngresosBarberoDTO;
import cl.varela.barberia.response.ResponseDTO;
import cl.varela.barberia.service.IReporteIngresosBarberoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes/ingresos-barbero")
@Tag(name = "Reportes de Ingresos por Barbero", description = "Controlador para reportes de ingresos y reservas completadas por barbero")
public class ReporteIngresosBarberoController {

    @Autowired
    private IReporteIngresosBarberoService reporteService;

    @Operation(summary = "Obtener el reporte de ingresos y reservas completadas para un barbero específico")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reporte obtenido correctamente"),
        @ApiResponse(responseCode = "404", description = "Barbero no encontrado")
    })
    @GetMapping("/{idBarbero}")
    public ResponseEntity<ResponseDTO> getReporteByBarberoId(@PathVariable Integer idBarbero) {
        try {
            ReporteIngresosBarberoDTO reporte = reporteService.obtenerReportePorBarberoId(idBarbero);
            return ResponseEntity.ok(new ResponseDTO("Reporte de ingresos obtenido correctamente", List.of(reporte)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ResponseDTO("ERR-404", e.getMessage(), Map.of("error", e.getMessage())));
        }
    }

    @Operation(summary = "Listar todos los reportes de ingresos por barbero")
    @ApiResponse(responseCode = "200", description = "Listado de reportes obtenido correctamente")
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllReportes() {
        List<ReporteIngresosBarberoDTO> reportes = reporteService.listarTodosLosReportes();
        return ResponseEntity.ok(new ResponseDTO("Listado de reportes de ingresos obtenido correctamente", reportes));
    }
}
