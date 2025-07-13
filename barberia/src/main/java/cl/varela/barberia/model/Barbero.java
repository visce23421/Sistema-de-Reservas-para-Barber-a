package cl.varela.barberia.model;
import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Entity
//nombre de la tabla en bd
@Table(name="BARBERO")
//JsonIgnoreProperties, Los objetos vacíos los reemplaza por null
@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Barbero {

@Id 
@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "barbero_seq")
@SequenceGenerator(name = "barbero_seq", sequenceName = "SEQ_BARBERO", allocationSize = 1)
@Column(name = "id_barbero")     
private Integer idbarbero;
@Column(name = "nombre")
private String nombre;   
@Column(name = "numero_telefonico")     
private String numeroTelefonico;
}
