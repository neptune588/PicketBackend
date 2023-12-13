package com.swyg.picketbackend.global.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "3조 Picket API 명세서"
                ,description = "Picket API 명세서"
        ))

@Configuration
public class SwaggerConfig {
}
