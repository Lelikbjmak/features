package com.innowise.queryparametrization.manual_specification_call_revision.service;

import com.innowise.datastarter.service.AbstractGenericCrudService;
import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.manual_specification_call_revision.dto.ProductFilterDto;
import com.innowise.queryparametrization.manual_specification_call_revision.util.ProductSpecificationUtil;
import com.innowise.queryparametrization.manual_specification_call_revision.util.SpecificationUtil;
import com.innowise.queryparametrization.repository.ProductRepository;
import com.innowise.queryparametrization.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class ManualProductServiceImpl extends
        AbstractGenericCrudService<Product, String, ProductRepository> implements ProductService {

    public ManualProductServiceImpl(ProductRepository repository) {
        super(repository);
    }

    public Page<Product> findPage(ProductFilterDto productFilterDto, Pageable pageable) {
        Specification<Product> spec = null;

        spec = SpecificationUtil.addFieldSpec(productFilterDto.id(), spec,
                ProductSpecificationUtil::idEquals);
        spec = SpecificationUtil.addFieldSpec(productFilterDto.createdAt(), spec,
                ProductSpecificationUtil::createdAtGreaterThan);

        return repository.findAll(spec, pageable);
    }
}