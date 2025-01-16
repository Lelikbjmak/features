package com.innowise.innowisesecuritystarter.security.inmemory.config.properties;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@ConfigurationProperties("app.security.auth.in-memory")
public class InMemoryAuthenticationConfigProperties {

  String username;

  String password;

  String role;

  boolean enabled;
}
