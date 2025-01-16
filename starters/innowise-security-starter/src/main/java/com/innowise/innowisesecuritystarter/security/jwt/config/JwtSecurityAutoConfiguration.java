package com.innowise.innowisesecuritystarter.security.jwt.config;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.innowisesecuritystarter.annotation.InjectAuthenticationConfiguration;
import com.innowise.innowisesecuritystarter.security.jwt.JwtAuthenticationFilter;
import com.innowise.innowisesecuritystarter.security.jwt.config.properties.JwtSecurityConfigProperties;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@ComponentScan("com.innowise")
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
@EnableConfigurationProperties(JwtSecurityConfigProperties.class)
@ConditionalOnProperty(name = "app.security.auth.method", havingValue = "jwt")
public class JwtSecurityAutoConfiguration {

  AuthenticationEntryPoint commonAuthenticationEntryPoint;
  AccessDeniedHandler commonAccessDeniedHandler;

  JwtAuthenticationFilter jwtAuthenticationFilter;

  @InjectAuthenticationConfiguration
  List<RequestMatcher> notAuthenticatedMatchers;

  CorsConfigurationSource corsConfigurationSource;

  @Bean
  public SecurityFilterChain jwtSecurityFilterChain(HttpSecurity http) throws Exception {

    http.cors(httpSecurityCorsConfigurer ->
            httpSecurityCorsConfigurer.configurationSource(corsConfigurationSource))
        .csrf(AbstractHttpConfigurer::disable)

        .authorizeHttpRequests((routes) -> {
          for (RequestMatcher matcher : notAuthenticatedMatchers) {
            routes.requestMatchers(matcher).permitAll();
          }
          routes.anyRequest().authenticated();
        }).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))

        .exceptionHandling((exceptionHandling) -> {
          exceptionHandling.authenticationEntryPoint(commonAuthenticationEntryPoint);
          exceptionHandling.accessDeniedHandler(commonAccessDeniedHandler);
        });

    return http.build();
  }
}
