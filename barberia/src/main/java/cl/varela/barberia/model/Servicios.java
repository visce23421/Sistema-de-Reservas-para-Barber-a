package cl.varela.barberia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name = "SERVICIO")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Servicios {

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "servicio_seq")
    @SequenceGenerator(name = "servicio_seq", sequenceName = "SEQ_SERVICIO", allocationSize = 1)
    @Column(name = "id_servicio")     
    private Integer idServicio;


    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "DURACION")
    private Integer duracion; // minutos

    @Column(name = "PRECIO")
    private Integer precio;
}