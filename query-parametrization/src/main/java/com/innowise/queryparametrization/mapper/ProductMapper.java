package com.innowise.queryparametrization.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.innowise.datastarter.mapper.GenericMapper;
import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.dto.ProductDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING, uses = CategoryMapper.class)
public interface ProductMapper extends GenericMapper<Product, ProductDto> {

}
