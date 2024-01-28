package com.innowise.crudlib.config;

import static lombok.AccessLevel.PRIVATE;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@Configuration
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RedisAutoConfiguration {

  @Bean
  public RedisTemplate<String, String> redisStringStringTemplate(
      RedisConnectionFactory connectionFactory) {
    var template = new RedisTemplate<String, String>();
    template.setEnableTransactionSupport(true);
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
    template.setValueSerializer(new StringRedisSerializer(StandardCharsets.UTF_8));
    template.afterPropertiesSet();

    return template;
  }

  @Bean
  public RedisTemplate<String, Object> redisStringObjectTemplate(
      RedisConnectionFactory connectionFactory) {
    var objectMapper = new ObjectMapper();
    var ptv = BasicPolymorphicTypeValidator.builder()
        .allowIfBaseType(Map.class)
        .allowIfBaseType(Object.class)
        .build();
    objectMapper.activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.EVERYTHING,
        JsonTypeInfo.As.PROPERTY);

    var template = new RedisTemplate<String, Object>();
    template.setEnableTransactionSupport(true);
    template.setConnectionFactory(connectionFactory);
    template.setKeySerializer(new StringRedisSerializer());
    template.setValueSerializer(new GenericJackson2JsonRedisSerializer(objectMapper));
    template.afterPropertiesSet();

    return template;
  }
}
