package cl.varela.barberia.dto;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ClienteDTO {

   private Integer idCliente;
    private String nombre;

   
    private String telefono;

   
    private String correo;

}
