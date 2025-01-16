package com.innowise.graphql.testgraphql.config;

import graphql.scalars.ExtendedScalars;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Slf4j
@Configuration
public class GraphQLConfig {

  @Bean
  public RuntimeWiringConfigurer runtimeWiringConfigurer() {
    log.debug("Hello from debug");
    return wiringBuilder -> wiringBuilder
        .scalar(ExtendedScalars.DateTime)
        .scalar(ExtendedScalars.LocalTime);
  }

  @Bean
  @Primary
  public CorsConfigurationSource corsConfigurationSourceReact() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(
        List.of("*", "http://localhost:3000/**", "http://localhost:9090/**"));
    configuration.setAllowedMethods(
        List.of("*", "http://localhost:3000/**", "http://localhost:9090/**"));
    configuration.setAllowedHeaders(
        List.of("*", "http://localhost:3000/**", "http://localhost:9090/**"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
