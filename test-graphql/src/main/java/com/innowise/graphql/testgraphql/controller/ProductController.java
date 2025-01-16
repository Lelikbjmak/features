package com.innowise.graphql.testgraphql.controller;

import com.innowise.graphql.testgraphql.entity.Category;
import com.innowise.graphql.testgraphql.entity.Product;
import com.innowise.graphql.testgraphql.service.impl.ProductService;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {

  ProductService productService;

  @MutationMapping("saveProduct")
  public Product createProduct(@Argument Product product) {
    return productService.createProduct(product);
  }

  @QueryMapping("findProductsByCategory")
  public List<Product> createProduct(@Argument Category category) {
    return productService.findAllByCategory(category);
  }
}
