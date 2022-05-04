package org.afp.schoolregistration.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI openAPI() {
    return new OpenAPI().info(getInfo()).externalDocs(getExternalDocs());
  }

  private ExternalDocumentation getExternalDocs() {
    return new ExternalDocumentation()
            .description("Home Page")
            .url("https://metadata.io/");
  }

  private Info getInfo() {
    return new Info()
            .title("School registration system")
            .description("School registration system code challenge for metadata.io")
            .version("0.0.1");
  }

}
