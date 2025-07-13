package cl.varela.barberia.service.impl;

import cl.varela.barberia.dto.ReporteIngresosBarberoDTO;
import cl.varela.barberia.exceptions.ConflictException;
import cl.varela.barberia.model.Barbero;
import cl.varela.barberia.model.ReporteIngresosBarbero;
import cl.varela.barberia.repository.BarberoRepository;
import cl.varela.barberia.repository.ReporteIngresosBarberoRepository;
import cl.varela.barberia.service.IReporteIngresosBarberoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReporteIngresosBarberoServiceImpl implements IReporteIngresosBarberoService {

    @Autowired
    private ReporteIngresosBarberoRepository reporteRepository;

    @Autowired
    private BarberoRepository barberoRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ReporteIngresosBarberoDTO obtenerReportePorBarberoId(Integer idBarbero) {
        Barbero barbero = barberoRepository.findById(idBarbero)
                .orElseThrow(() -> new ConflictException("Barbero no encontrado con ID: " + idBarbero));

        ReporteIngresosBarbero reporte = reporteRepository.findByBarbero(barbero)
                .orElse(new ReporteIngresosBarbero(barbero)); // Si no existe, crea uno nuevo (no persistido aún)

        return modelMapper.map(reporte, ReporteIngresosBarberoDTO.class);
    }

    @Override
    public List<ReporteIngresosBarberoDTO> listarTodosLosReportes() {
        return reporteRepository.findAll().stream()
                .map(reporte -> modelMapper.map(reporte, ReporteIngresosBarberoDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void actualizarReporte(Integer idBarbero, Integer montoServicio) {
        Barbero barbero = barberoRepository.findById(idBarbero)
                .orElseThrow(() -> new IllegalArgumentException("Barbero no encontrado con ID: " + idBarbero));

                LocalDate fechaActual = LocalDate.now();

        ReporteIngresosBarbero reporte = reporteRepository.findByBarberoAndFecha(barbero,fechaActual)
                .orElseGet(() -> {
                    ReporteIngresosBarbero nuevoReporte = new ReporteIngresosBarbero(barbero);
                    nuevoReporte.setFecha(fechaActual);
                    return reporteRepository.save(nuevoReporte); // Persiste el nuevo reporte si no existe
                });

        reporte.setTotalReservasCompletadas(reporte.getTotalReservasCompletadas() + 1);
        reporte.setTotalIngresosGenerados(reporte.getTotalIngresosGenerados() + montoServicio);
        reporteRepository.save(reporte);
    }

        
}
