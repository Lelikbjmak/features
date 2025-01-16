package com.innowise.innowisesecuritystarter.config.properties;


import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.security.auth.cors")
public record CorsSecurityConfigProperties(

    List<String> allowedOrigins,

    List<String> allowedMethods,

    List<String> allowedHeaders,

    String pattern
) {

}
