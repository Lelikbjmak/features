package com.innowise.queryparametrization.repository;


import com.innowise.datastarter.repository.GenericCrudRepository;
import com.innowise.queryparametrization.domain.Person;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GenericCrudRepository<Person, String> {

}
