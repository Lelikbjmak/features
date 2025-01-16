package com.innowise.innowisesecuritystarter.security.oauth2.config;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.innowisesecuritystarter.annotation.InjectAuthenticationConfiguration;
import com.innowise.innowisesecuritystarter.security.jwt.config.JwtSecurityAutoConfiguration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@ComponentScan("com.innowise")
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AutoConfigureAfter(JwtSecurityAutoConfiguration.class)
@ConditionalOnProperty(name = "app.security.auth.method", havingValue = "oauth2")
public class Oauth2SecurityAutoConfiguration {

  AuthenticationEntryPoint commonAuthenticationEntryPoint;
  AccessDeniedHandler commonAccessDeniedHandler;

  @InjectAuthenticationConfiguration
  List<RequestMatcher> notAuthenticatedMatchers;

  CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain oauth2SecurityFilterChain(HttpSecurity http) throws Exception {

    http
        .cors(httpSecurityCorsConfigurer ->
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
        .csrf(AbstractHttpConfigurer::disable)

        .authorizeHttpRequests((routes) -> {
          for (RequestMatcher matcher : notAuthenticatedMatchers) {
            routes.requestMatchers(matcher).permitAll();
          }
          routes.anyRequest().authenticated();
        })

        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))

        .exceptionHandling((exceptionHandling) -> {
          exceptionHandling.authenticationEntryPoint(commonAuthenticationEntryPoint);
          exceptionHandling.accessDeniedHandler(commonAccessDeniedHandler);
        })

        .oauth2ResourceServer(oauth -> oauth.jwt(
            jwtConfigurer -> jwtConfigurer.jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .decoder(decoder())
                .jwtAuthenticationConverter(jwtAuthenticationConverter())));

    return http.build();
  }

  @Bean
  public JwtDecoder decoder() {
    return NimbusJwtDecoder.withIssuerLocation("https://accounts.google.com").build();
  }

  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
    grantedAuthoritiesConverter.setAuthorityPrefix("");

    jwtConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtConverter;
  }
}
