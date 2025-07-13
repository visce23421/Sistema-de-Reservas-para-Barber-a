package cl.varela.barberia.model;

import jakarta.persistence.*;
import lombok.*;
@Entity 
@Table(name = "HORARIO_BARBERO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HorarioBarbero {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "horario_barbero_seq")
    @SequenceGenerator(name = "horario_barbero_seq", sequenceName = "SEQ_HORARIO_BARBERO", allocationSize = 1)
    @Column(name = "ID_HORARIO")
    private Integer idHorario;

    @ManyToOne
    @JoinColumn(name = "ID_BARBERO")
    private Barbero barbero;

    @ManyToOne
    @JoinColumn(name = "ID_DIA")
    private DiaSemana diaSemana;

    @Column(name = "HORA_INICIO")
    private String horaInicio; 

    @Column(name = "HORA_FIN")
    private String horaFin;    
}