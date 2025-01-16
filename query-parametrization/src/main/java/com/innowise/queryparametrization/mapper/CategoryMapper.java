package com.innowise.queryparametrization.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.innowise.datastarter.mapper.GenericMapper;
import com.innowise.queryparametrization.domain.Category;
import com.innowise.queryparametrization.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface CategoryMapper extends GenericMapper<Category, CategoryDto> {

}
