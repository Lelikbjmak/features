package com.innowise.crudlib.repository.redis.impl;

import com.innowise.crudlib.config.RedisAutoConfiguration;
import com.innowise.crudlib.repository.redis.AbstractRedisCrudRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnBean(RedisAutoConfiguration.class)
public class JsonRedisRepository extends AbstractRedisCrudRepository<String, Object> {

    public JsonRedisRepository(@Qualifier("redisStringObjectTemplate") RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate);
    }
}
