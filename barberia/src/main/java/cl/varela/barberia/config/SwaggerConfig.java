package cl.varela.barberia.config;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI barberiaOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Barbería API")
                        .description("API para el sistema de reservas de barbería")
                        .version("1.0"));
    }
}