package cl.varela.barberia.dto;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;






@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HorarioBarberoDTO {

   
    
    private BarberoDTO barbero;


    private DiaSemanaDTO diaSemana;

   
    private String horaInicio; 

    
    private String horaFin;    
}
