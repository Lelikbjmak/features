package com.innowise.queryparametrization.repository;

import com.innowise.datastarter.repository.GenericCrudRepository;
import com.innowise.queryparametrization.domain.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressCategory extends GenericCrudRepository<Product, String> {

}
