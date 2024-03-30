package com.innowise.datastarter.repository.redis;

import com.innowise.datastarter.repository.KeyValueCrudRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Optional;

import static lombok.AccessLevel.PROTECTED;

@RequiredArgsConstructor
@FieldDefaults(level = PROTECTED, makeFinal = true)
public abstract class AbstractRedisCrudRepository<K, V> implements KeyValueCrudRepository<K, V> {

    RedisTemplate<K, V> redisTemplate;

    @Override
    @Transactional
    public <S extends V> S save(K key, S value) {
        redisTemplate.opsForValue().setIfAbsent(key, value);
        return value;
    }

    @Override
    @Transactional
    public <S extends V> S save(K key, S value, Duration expiration) {
        redisTemplate.opsForValue().setIfAbsent(key, value, expiration);
        return value;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<V> get(K key) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key));
    }

    @Override
    @Transactional
    public void delete(K key) {
        redisTemplate.delete(key);
    }
}
