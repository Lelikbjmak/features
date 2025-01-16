package com.innowise.innowisesecuritystarter.config.http;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum HttpMethod {

  GET("GET"),
  POST("POST"),
  PUT("POST"),
  PATCH("PATCH"),
  DELETE("DELETE");

  String name;
}
