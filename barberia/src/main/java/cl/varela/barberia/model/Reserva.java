package cl.varela.barberia.model;
import java.time.LocalDate;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.varela.barberia.enums.EstadoReserva;
import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown=true)

@Entity
@Table(name = "RESERVA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reserva_seq")
    @SequenceGenerator(name = "reserva_seq", sequenceName = "SEQ_RESERVA", allocationSize = 1)
    @Column(name = "id_reserva")     
    private Integer idReserva;


    @ManyToOne
    @JoinColumn(name = "ID_CLIENTE")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "ID_BARBERO",referencedColumnName = "ID_BARBERO")
    private Barbero barbero;

    @ManyToOne
    @JoinColumn(name = "ID_SERVICIO")
    private Servicios servicios;

    @Column(name = "FECHA")
    private LocalDate fecha;

    @Column(name = "HORA")
    private String hora;

    @Enumerated(EnumType.STRING)
    @Column(name = "ESTADO")
    private EstadoReserva estado;   
}

