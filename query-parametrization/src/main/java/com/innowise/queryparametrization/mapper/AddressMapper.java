package com.innowise.queryparametrization.mapper;

import com.innowise.corelib.mapper.GenericMapper;
import com.innowise.queryparametrization.domain.Address;
import com.innowise.queryparametrization.domain.Category;
import com.innowise.queryparametrization.dto.AddressDto;
import com.innowise.queryparametrization.dto.CategoryDto;
import org.mapstruct.Mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public interface AddressMapper extends GenericMapper<Address, AddressDto> {

}
