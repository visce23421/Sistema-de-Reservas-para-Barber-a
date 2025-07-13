package cl.varela.barberia.dto;

import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteIngresosBarberoDTO {
    private Integer idReporteBarbero;
    private BarberoDTO barbero; // Para incluir la información del barbero
    private LocalDate fecha;
    private Integer totalReservasCompletadas;
    private Integer totalIngresosGenerados;
}
