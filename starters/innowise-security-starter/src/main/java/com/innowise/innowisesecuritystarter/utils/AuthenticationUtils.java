package com.innowise.innowisesecuritystarter.utils;

import java.util.Map;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@UtilityClass
public class AuthenticationUtils {

  private static final String USERNAME_AUTH_KEY = "username";

  public static Authentication authentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }

  public static Authentication authentication(Authentication authentication) {
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return authentication();
  }

  public static Object claim(String key) {
    if (Objects.equals(USERNAME_AUTH_KEY, key)) {
      return authentication().getPrincipal().toString();
    }
    var details = authentication().getDetails();
    if (details instanceof Map) {
      return ((Map<?, ?>) details).get(key);
    }

    return null;
  }
}
