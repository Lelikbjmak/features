package com.innowise.dynamicspecification.config;

import com.innowise.dynamicspecification.service.DynamicSpecificationBuilder;
import com.innowise.dynamicspecification.specification.DynamicSpecificationContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@ConditionalOnBean(DynamicSpecificationBuilder.class)
public class DynamicSpecificationAutoConfiguration {

  @Bean
  @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
  public DynamicSpecificationContext<?> specificationHolder() {
    return new DynamicSpecificationContext<>();
  }
}
