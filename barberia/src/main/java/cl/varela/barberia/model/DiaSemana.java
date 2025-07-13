package cl.varela.barberia.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Table(name = "DIA_SEMANA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DiaSemana {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dia_semana_seq")
    @SequenceGenerator(name = "dia_semana_seq", sequenceName = "SEQ_DIA_SEMANA", allocationSize = 1)
    @Column(name = "ID_DIA")
    private Integer idDia;

    @Column(name = "NOMBRE", nullable = false, unique = true)
    private String nombre; 
}