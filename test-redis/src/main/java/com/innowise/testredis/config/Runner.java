package com.innowise.testredis.config;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {

  private final RedisTemplate<String, Employee> redisStringObjectTemplate;

  private final RedisTemplate<String, String> redisStringStringTemplate;

  @Override
  public void run(String... args) throws Exception {
//    redisStringStringTemplate.opsForValue().set("hello", "world");
//    redisStringStringTemplate.opsForList().leftPush("1","2");
//    redisStringStringTemplate.opsForList().leftPush("2", "3");
//    redisStringStringTemplate.opsForList().leftPush("2", "4");
//    redisStringStringTemplate.opsForList().rightPush("3","5");
    Integer a = redisStringStringTemplate.execute(RedisScript.of(new ClassPathResource("a.lua")),
        List.of("1"), "5");

    System.out.println(a);
  }
}
