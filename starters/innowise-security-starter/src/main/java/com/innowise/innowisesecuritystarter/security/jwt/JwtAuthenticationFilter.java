package com.innowise.innowisesecuritystarter.security.jwt;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static lombok.AccessLevel.PRIVATE;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.innowise.innowisesecuritystarter.security.jwt.config.properties.JwtSecurityConfigProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  static String AUTHORIZATION_PREFIX = "Bearer ";
  static String AUTHORITIES_CLAIM = "authorities";
  static String USER_ID_CLAIM = "user_id";
  static String EMAIL_CLAIM = "email";
  static String AUTH_DETAILS_USER_ID_KEY = "userId";
  static String AUTH_DETAILS_EMAIL_KEY = "email";

  JwtSecurityConfigProperties securityConfigProperties;

  @Override
  @SneakyThrows
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) {

    String authHeader = request.getHeader(AUTHORIZATION);

    if (nonNull(authHeader) && authHeader.startsWith(AUTHORIZATION_PREFIX)) {
      try {
        String token = authHeader.substring(AUTHORIZATION_PREFIX.length());

        Algorithm algorithm = Algorithm.HMAC256(securityConfigProperties.secretKey());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJwt = jwtVerifier.verify(token);

        String username = decodedJwt.getSubject();
        String userId = decodedJwt.getClaim(USER_ID_CLAIM).asString();
        String email = decodedJwt.getClaim(EMAIL_CLAIM).asString();
        var authorities = Arrays
            .stream(decodedJwt.getClaim(AUTHORITIES_CLAIM).asArray(String.class))
            .map(SimpleGrantedAuthority::new)
            .collect(toList());

        var authentication =
            new UsernamePasswordAuthenticationToken(username, token, authorities);
        var authenticationDetails = new HashMap<>();
        authenticationDetails.put(AUTH_DETAILS_USER_ID_KEY, userId);
        authenticationDetails.put(AUTH_DETAILS_EMAIL_KEY, email);
        authentication.setDetails(authenticationDetails);

        SecurityContextHolder.getContext().setAuthentication(authentication);
      } catch (JWTVerificationException e) {
        response.setHeader("error", "Invalid JWT token or token expired.");
        response.setStatus(FORBIDDEN.value());
      } catch (Exception e) {
        log.error("Error during authorization: {}.", e.getMessage());
        response.setStatus(INTERNAL_SERVER_ERROR.value());
      }
    }

    filterChain.doFilter(request, response);
  }
}
