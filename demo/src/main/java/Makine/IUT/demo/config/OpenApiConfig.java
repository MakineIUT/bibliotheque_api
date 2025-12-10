package Makine.IUT.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Bibliothèque API")
                        .version("1.0")
                        .description("API REST pour la gestion d'une bibliothèque - Livres et Auteurs")
                        .contact(new Contact()
                                .name("Support Bibliothèque")
                                .email("support@bibliotheque.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .components(new Components()
                        .addSecuritySchemes("X-API-KEY",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.APIKEY)
                                        .in(SecurityScheme.In.HEADER)
                                        .name("X-API-KEY")
                                        .description("Clé API requise pour POST/PUT/DELETE (Valeur: secret-api-key-2025)")))
                .addSecurityItem(new SecurityRequirement().addList("X-API-KEY"));
    }
}
