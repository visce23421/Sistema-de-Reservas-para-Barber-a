package cl.varela.barberia;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CFG {
    // Definimos que la clase ModelMapper es un Bean
    // si no la utilizamos no podemos utilizarla como inyeccion de dependencia
    // o sea no puedo usar Autowired    
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
