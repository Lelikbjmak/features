package com.innowise.datastarter.repository.redis.impl;

import com.innowise.datastarter.config.RedisAutoConfiguration;
import com.innowise.datastarter.repository.redis.AbstractRedisCrudRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RedisAutoConfiguration.class)
public class JsonRedisRepository extends AbstractRedisCrudRepository<String, Object> {

    public JsonRedisRepository(@Qualifier("redisStringObjectTemplate") RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }
}
