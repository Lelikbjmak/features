package com.innowise.queryparametrization.repository;


import com.innowise.crudlib.repository.GenericCrudRepository;
import com.innowise.queryparametrization.domain.Person;
import com.innowise.queryparametrization.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GenericCrudRepository<Person, String> {

}
