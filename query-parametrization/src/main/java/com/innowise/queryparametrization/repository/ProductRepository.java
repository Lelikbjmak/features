package com.innowise.queryparametrization.repository;


import com.innowise.queryparametrization.domain.Product;
import com.innowise.datastarter.repository.GenericCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends GenericCrudRepository<Product, String> {

}
