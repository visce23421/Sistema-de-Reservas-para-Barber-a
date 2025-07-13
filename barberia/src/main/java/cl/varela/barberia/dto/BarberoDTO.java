package cl.varela.barberia.dto;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BarberoDTO {
private Integer idbarbero;
private String nombre;   

private String numeroTelefonico;

}
