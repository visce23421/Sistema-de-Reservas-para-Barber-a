package cl.varela.barberia.service;

import cl.varela.barberia.dto.ReporteIngresosBarberoDTO;

import java.util.List;

public interface IReporteIngresosBarberoService {
    ReporteIngresosBarberoDTO obtenerReportePorBarberoId(Integer idBarbero);
    List<ReporteIngresosBarberoDTO> listarTodosLosReportes();
  
    void actualizarReporte(Integer idBarbero, Integer montoServicio);
}
