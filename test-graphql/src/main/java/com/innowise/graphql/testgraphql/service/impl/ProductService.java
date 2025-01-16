package com.innowise.graphql.testgraphql.service.impl;

import com.innowise.graphql.testgraphql.entity.Category;
import com.innowise.graphql.testgraphql.entity.Product;
import com.innowise.graphql.testgraphql.repository.ProductRepository;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductService {

  ProductRepository productRepository;

  public Product createProduct(Product product) {
    product.setId(UUID.randomUUID().toString());
    return productRepository.save(product);
  }

  public List<Product> findAllByCategory(Category category) {
    return productRepository.findByCategory(category);
  }
}
