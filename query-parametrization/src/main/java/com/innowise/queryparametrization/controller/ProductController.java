package com.innowise.queryparametrization.controller;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.queryparametrization.dto.ProductDto;
import com.innowise.queryparametrization.mapper.ProductMapper;
import com.innowise.queryparametrization.service.ProductService;
import com.innowise.queryparametrization.specification_context_revision.annotation.EnableDynamicSpecification;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class ProductController {

    ProductService productService;

    ProductMapper productMapper;

    @GetMapping
    @EnableDynamicSpecification
    public Page<ProductDto> findAllWithFilter(
            @RequestParam MultiValueMap<String, Object> filters, Pageable pageable) {
        return null;
    }
}
