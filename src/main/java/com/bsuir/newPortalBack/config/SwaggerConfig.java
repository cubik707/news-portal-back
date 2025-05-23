package com.bsuir.newPortalBack.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI getOpenAPI() {
    return new OpenAPI()
      .servers(
        List.of(new Server())
      )
      .info(
        new Info().title("News Portal API")
      );
  }
}
