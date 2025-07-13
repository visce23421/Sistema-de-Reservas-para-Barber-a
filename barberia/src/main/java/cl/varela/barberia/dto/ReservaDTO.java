package cl.varela.barberia.dto;


import lombok.*;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import cl.varela.barberia.enums.EstadoReserva;





@JsonIgnoreProperties(ignoreUnknown=true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReservaDTO {
    
    private ClienteDTO cliente;

    private BarberoDTO barbero;
    
    private ServiciosDTO servicios;

    private LocalDate fecha;

    private String hora;

    private EstadoReserva estado;
}
