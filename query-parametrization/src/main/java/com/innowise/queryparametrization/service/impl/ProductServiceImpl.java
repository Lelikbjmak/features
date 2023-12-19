package com.innowise.queryparametrization.service.impl;

import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.repository.ProductRepository;
import com.innowise.queryparametrization.service.ProductService;
import com.innowise.crudlib.service.AbstractGenericCrudService;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends
    AbstractGenericCrudService<Product, String, ProductRepository> implements ProductService {

  public ProductServiceImpl(ProductRepository repository) {
    super(repository);
  }

}
