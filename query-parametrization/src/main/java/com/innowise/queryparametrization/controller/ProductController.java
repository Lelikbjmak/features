package com.innowise.queryparametrization.controller;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.dto.ProductDto;
import com.innowise.queryparametrization.mapper.ProductMapper;
import com.innowise.queryparametrization.service.ProductService;
import com.innowise.queryparametrization.revision2.SpecificationService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProductController {

  SpecificationService specificationService;

  ProductService productService;

  ProductMapper productMapper;

  @GetMapping
  public Page<ProductDto> findAllWithFilter(@RequestParam MultiValueMap<String, Object> filters,
      Pageable pageable) {
    Specification<Product> spec = specificationService.generateSpecification(filters);

    return productService.findPage(spec, pageable).map(productMapper::toDto);
  }
}
