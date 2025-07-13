package cl.varela.barberia.dto;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class ServiciosDTO {
 private Integer idServicio;
    private String nombre;

   
    private Integer duracion; // minutos

   
    private Integer precio;
}
