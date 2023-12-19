package com.innowise.crudlib.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface CrudService<E, ID> {

  <S extends E> S save(S entity);

  <S extends E> S saveAndFlush(S entity);

  <S extends E> Iterable<S> saveAll(Iterable<S> entities);

  E findById(ID id);

  List<E> findAll();

  List<E> findAllById(Iterable<ID> idIterable);

  Page<E> findPage(Pageable pageable);

  Page<E> findPage(Specification<E> specification, Pageable pageable);

  void deleteById(ID id);

  void delete(E entity);

  void deleteAll(Iterable<? extends E> entities);

  void deleteAll();
}
