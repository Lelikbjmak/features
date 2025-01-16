package com.innowise.innowisesecuritystarter.security.jwt.config.properties;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("app.security.auth.jwt")
public record JwtSecurityConfigProperties(

    String secretKey,

    Duration accessTokenExpiration,

    Duration refreshTokenExpiration
) {

}
