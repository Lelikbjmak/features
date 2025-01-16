package com.innowise.innowisesecuritystarter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;


@Slf4j
@RequiredArgsConstructor
public class CommonAccessDeniedHandler implements AccessDeniedHandler {

  private final ObjectMapper objectMapper;

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response,
      AccessDeniedException accessDeniedException) throws IOException {

    log.debug("Access denied for ip: {}.\tPath: {}", request.getRemoteAddr(),
        request.getServletPath());

    HttpStatus status = HttpStatus.FORBIDDEN;

    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getOutputStream()
        .print(objectMapper.writeValueAsString(accessDeniedException.getMessage()));
  }
}
