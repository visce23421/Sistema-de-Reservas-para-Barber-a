package cl.varela.barberia.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import lombok.*;
@JsonIgnoreProperties(ignoreUnknown=true)
@Entity
@Table(name = "CLIENTE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

    @Id
   
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_seq")
    @SequenceGenerator(name = "cliente_seq", sequenceName = "SEQ_CLIENTE", allocationSize = 1)
    @Column(name = "id_cliente")     
    private Integer idCliente;

    

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "correo")
    private String correo;
}
