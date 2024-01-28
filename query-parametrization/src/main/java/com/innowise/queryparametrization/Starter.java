package com.innowise.queryparametrization;

import com.innowise.queryparametrization.domain.Address;
import com.innowise.queryparametrization.domain.Category;
import com.innowise.queryparametrization.domain.Person;
import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.repository.PersonRepository;
import com.innowise.queryparametrization.repository.ProductRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

//@Service
@RequiredArgsConstructor
public class Starter implements CommandLineRunner {

  private final ProductRepository productRepository;
  private final PersonRepository personRepository;

  @Override
  public void run(String... args) {
    List<Person> products = new ArrayList<>();

    for (int i = 0; i < 100; i++) {
      var cat = new Address();
      cat.setName("Cata " + (i % 10));
      Person product = new Person();
      product.setName("Product " + i);
      product.setType("Type " + (i % 10));
      product.setAddress(Set.of(cat));
      product.setPrice(new BigDecimal(new Random().nextInt(1000))); // Random price

      products.add(product);
    }

    personRepository.saveAll(products);
  }
}
