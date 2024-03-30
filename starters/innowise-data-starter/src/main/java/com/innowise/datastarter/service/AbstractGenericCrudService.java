package com.innowise.datastarter.service;

import static lombok.AccessLevel.PROTECTED;

import com.innowise.datastarter.repository.GenericCrudRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = PROTECTED)
public abstract class AbstractGenericCrudService<E, ID, R extends GenericCrudRepository<E, ID>> implements
    CrudService<E, ID> {

  R repository;

  @Override
  @Transactional
  public <S extends E> S save(S entity) {
    return repository.save(entity);
  }

  @Override
  @Transactional
  public <S extends E> S saveAndFlush(S entity) {
    return repository.save(entity);
  }

  @Override
  @Transactional
  public <S extends E> Iterable<S> saveAll(Iterable<S> entities) {
    return repository.saveAll(entities);
  }

  @Override
  @Transactional(readOnly = true)
  public E findById(ID id) {
    return repository.findById(id)
        .orElseThrow(EntityNotFoundException::new);
  }

  @Override
  @Transactional(readOnly = true)
  public List<E> findAll() {
    return repository.findAll();
  }

  @Override
  @Transactional
  public Page<E> findPage(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  @Transactional
  public Page<E> findPage(Specification<E> specification, Pageable pageable) {
    return repository.findAll(specification, pageable);
  }


  @Override
  @Transactional(readOnly = true)
  public List<E> findAllById(Iterable<ID> idIterable) {
    return repository.findAllById(idIterable);
  }

  @Override
  @Transactional
  public void deleteById(ID id) {
    repository.deleteById(id);
  }

  @Override
  @Transactional
  public void delete(E entity) {
    repository.delete(entity);
  }

  @Override
  @Transactional
  public void deleteAll(Iterable<? extends E> entities) {
    repository.deleteAll(entities);
  }

  @Override
  @Transactional
  public void deleteAll() {
    repository.deleteAll();
  }
}
