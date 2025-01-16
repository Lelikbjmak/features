package com.innowise.testredis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisStandaloneConfig {

  private final RedisProperties redisProperties;

  @Bean
  public ObjectMapper redisObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    return mapper;
  }

  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    var config = new RedisStandaloneConfiguration(redisProperties.getHost(),
        redisProperties.getPort());
    config.setPassword(redisProperties.getPassword());
    return new LettuceConnectionFactory(config);
  }

  @Bean("redisStringObjectTemplate")
  public RedisTemplate<String, Employee> redisStringEmployeeTemplate(
      RedisConnectionFactory connectionFactory,
      @Qualifier("redisObjectMapper") ObjectMapper objectMapper) {

    var template = new RedisTemplate<String, Employee>();
    template.setEnableTransactionSupport(true);
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
    template.afterPropertiesSet();

    return template;
  }

  @Bean("redisStringStringTemplate")
  public RedisTemplate<String, String> redisStringStringTemplate(
      RedisConnectionFactory connectionFactory) {
    var template = new RedisTemplate<String, String>();
    template.setEnableTransactionSupport(true);
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new StringRedisSerializer());
    template.afterPropertiesSet();

    return template;
  }
}
