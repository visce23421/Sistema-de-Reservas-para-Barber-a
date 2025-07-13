package cl.varela.barberia.model;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "REPORTE_INGRESOS_BARBERO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteIngresosBarbero {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reporte_ingresos_barbero_seq")
    @SequenceGenerator(name = "reporte_ingresos_barbero_seq", sequenceName = "SEQ_REPORTE_INGRESOS_BARBERO", allocationSize = 1)
    @Column(name = "ID_REPORTE_BARBERO")
    private Integer idReporteBarbero;

    @OneToOne // Un barbero tiene un único reporte de ingresos acumulado
    @JoinColumn(name = "ID_BARBERO", referencedColumnName = "ID_BARBERO", unique = true, nullable = false)
    private Barbero barbero;
   @Column(name = "FECHA", nullable = false)  
    private LocalDate fecha; 
    @Column(name = "TOTAL_RESERVAS_COMPLETADAS", nullable = false)
    private Integer totalReservasCompletadas;

    @Column(name = "TOTAL_INGRESOS_GENERADOS", nullable = false)
    private Integer totalIngresosGenerados;

    // Constructor para inicializar nuevos reportes
    public ReporteIngresosBarbero(Barbero barbero) {
        this.barbero = barbero;
        this.totalReservasCompletadas = 0;
        this.totalIngresosGenerados = 0;
    }
}
