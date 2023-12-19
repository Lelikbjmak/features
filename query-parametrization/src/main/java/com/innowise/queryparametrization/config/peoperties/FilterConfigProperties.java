package com.innowise.queryparametrization.config.peoperties;

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
@ConfigurationProperties("app.filtration")
public class FilterConfigProperties {

  String operationParamKey;

  String valueFromParamKey;

  String valueToParamKey;
}
