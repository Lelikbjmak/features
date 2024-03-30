package com.innowise.queryparametrization.service.impl;

import com.innowise.datastarter.service.AbstractGenericCrudService;
import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.repository.ProductRepository;
import com.innowise.queryparametrization.service.ProductService;
import com.innowise.queryparametrization.specification_context_revision.annotation.WithDynamicSpecification;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Primary
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductServiceImpl extends
        AbstractGenericCrudService<Product, String, ProductRepository> implements ProductService {

    public ProductServiceImpl(ProductRepository repository) {
        super(repository);
    }

    @Override
    @WithDynamicSpecification
    public Page<Product> findPage(Pageable pageable) {
        return super.findPage(pageable);
    }
}
