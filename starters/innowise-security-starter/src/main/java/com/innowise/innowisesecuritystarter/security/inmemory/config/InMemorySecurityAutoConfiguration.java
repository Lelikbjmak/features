package com.innowise.innowisesecuritystarter.security.inmemory.config;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.innowisesecuritystarter.security.inmemory.config.properties.InMemoryAuthenticationConfigProperties;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 1)
@ConditionalOnProperty(name = "app.security.auth.in-memory.enabled", havingValue = "true")
public class InMemorySecurityAutoConfiguration {

  InMemoryAuthenticationConfigProperties inMemoryAuthenticationProperties;

  AuthenticationEntryPoint authenticationEntryPoint;
  AccessDeniedHandler accessDeniedHandler;

  @Bean
  public SecurityFilterChain inMemorySecurityFilterChain(HttpSecurity http) throws Exception {

    http
        .securityMatcher("/api/*/system/**")

        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(Customizer.withDefaults())

        .authenticationProvider(inMemoryAuthenticationProvider())

        .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS))

        .exceptionHandling((exceptionHandling) -> {
          exceptionHandling.authenticationEntryPoint(authenticationEntryPoint);
          exceptionHandling.accessDeniedHandler(accessDeniedHandler);
        });

    return http.build();
  }

  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean("inMemoryUserDetailsService")
  public UserDetailsService inMemoryUserDetailsService() {
    UserDetails system = User.builder()
        .passwordEncoder(rawPassword -> bCryptPasswordEncoder().encode(rawPassword))
        .username(inMemoryAuthenticationProperties.getUsername())
        .password(inMemoryAuthenticationProperties.getPassword())
        .roles(inMemoryAuthenticationProperties.getRole()).build();
    return new InMemoryUserDetailsManager(system);
  }

  @Bean
  public AuthenticationProvider inMemoryAuthenticationProvider() {
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(inMemoryUserDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
    return daoAuthenticationProvider;
  }
}
