package com.innowise.queryparametrization.repository;

import com.innowise.crudlib.repository.GenericCrudRepository;
import com.innowise.queryparametrization.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends GenericCrudRepository<Product, String> {

}
