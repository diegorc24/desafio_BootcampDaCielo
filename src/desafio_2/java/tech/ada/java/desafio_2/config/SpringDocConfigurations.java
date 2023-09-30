package tech.ada.java.desafio_2.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocConfigurations {

    @Value("${projeto.nome}")
    private String nomeProjeto;

    @Value("${projeto.descricao}")
    private String descricao;

    @Bean
    public OpenAPI customOpenAPI()
    {
        return new OpenAPI().components(new Components())
                .info(new Info()
                        .title(nomeProjeto)
                        .description(descricao)
                        .contact(new Contact()
                                .name("Time Backen")
                                .email("diego.r.cadoso@hotmail.com")));
    }
}

