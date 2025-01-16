package com.innowise.innowisesecuritystarter.config;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.innowisesecuritystarter.config.properties.CorsSecurityConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@EnableConfigurationProperties(CorsSecurityConfigProperties.class)
public class CommonSecurityConfig {

  CorsSecurityConfigProperties corsSecurityConfigProperties;

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(corsSecurityConfigProperties.allowedOrigins());
    configuration.setAllowedMethods(corsSecurityConfigProperties.allowedMethods());
    configuration.setAllowedHeaders(corsSecurityConfigProperties.allowedHeaders());
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration(corsSecurityConfigProperties.pattern(), configuration);
    return source;
  }

  @Bean
  @Primary
  public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
    return new CommonAccessDeniedHandler(objectMapper);
  }

  @Bean
  @Primary
  public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
    return new CommonAuthenticationEntryPoint(objectMapper);
  }
}
