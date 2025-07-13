package cl.varela.barberia.response;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter

public class ResponseDTO {
    private boolean ok;
	private String status;
	private String detalle;
    private int count ;
    private List<?> registros;
    private Map<String, Object> error;
    private LocalDateTime hora = LocalDateTime.now();
    // Creamos una DTO, no importando como sea
    // El alumno debe conprender que puede crear los construcores que estime conveniente

    //  Solo Recibe mensaje y Lista(arreglo) de Registros
    public ResponseDTO(String detalle,List<?> registros){
        if (registros== null) count=0; // No vienen registros
        else  count=registros.size();  // Si vienen registros
        ok = true;    // Asumo que resulto
        status = "OKE-0000";  // Respondo con un código cualquiera
        this.detalle=detalle;  // Envio el mensaje que llega por parámetro
        this.registros = registros;  //paso los registros
        this.error= null;
    }

    // Constructor para insert, update y delete
    public ResponseDTO(String detalle,boolean respuesta){
        count=0;   // Asumo cero registros
        ok=respuesta;  // Envio respuestas
        status="OKE-000";    // Status
        this.detalle=detalle;  // Envio mensaje recibido
        this.registros=null;   // no vienen registros
        this.error= null;
    }

    // Mensaje de Errores
    public ResponseDTO(String codigo,String detalle,Map<String, Object> error){
        count=0;   // Asumo cero registros
        ok=false;  // Envio respuestas
        status=codigo;    // Status
        this.detalle=detalle;  // Envio mensaje recibido
        this.error=error  ;  // Envio mensaje recibido
        this.registros=null;   // no vienen registros
        
    }

}

