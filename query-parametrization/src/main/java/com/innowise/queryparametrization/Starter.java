package com.innowise.queryparametrization;

import com.innowise.queryparametrization.domain.Category;
import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Starter implements CommandLineRunner {

  private final ProductRepository productRepository;

  @Override
  public void run(String... args) {
//    List<Product> products = new ArrayList<>();
//
//    for (int i = 0; i < 100; i++) {
//      var cat = new Category();
//      cat.setName("Cata " + (i % 10));
//      Product product = new Product();
//      product.setName("Product " + i);
//      product.setType("Type " + (i % 10));
//      product.setCategory(Set.of(cat));
//      product.setPrice(new BigDecimal(new Random().nextInt(1000))); // Random price
//
//      products.add(product);
//    }
//
//    productRepository.saveAll(products);
  }
}
