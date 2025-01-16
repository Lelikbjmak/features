package com.innowise.innowisesecuritystarter.annotation;

import com.innowise.innowisesecuritystarter.config.http.HttpMethod;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface Endpoint {

  String path();

  HttpMethod method();
}
