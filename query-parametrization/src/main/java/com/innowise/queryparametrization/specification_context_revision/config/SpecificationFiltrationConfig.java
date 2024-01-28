package com.innowise.queryparametrization.specification_context_revision.config;

import com.innowise.queryparametrization.specification_context_revision.SpecificationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class SpecificationFiltrationConfig {

    @Bean
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public SpecificationContext<?> specificationHolder() {
        return new SpecificationContext<>();
    }
}
