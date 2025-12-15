package portal_capacitaciones.portal_capacitaciones.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI portalCapacitacionesOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Portal de Capacitaciones API")
                        .description("API para gestión de cursos, usuarios, progresos e insignias")
                        .version("1.0.0"));
    }
}

