package com.innowise.crudlib.repository;

import java.time.Duration;
import java.util.Optional;

public interface KeyValueCrudRepository<K, V> {

    <S extends V> S save(K key, S value);

    /**
     * @param key        - key
     * @param value      - value
     * @param expiration - in seconds
     * @return - value
     */
    <S extends V> S save(K key, S value, Duration expiration);

    void delete(K key);

    Optional<V> get(K key);
}
