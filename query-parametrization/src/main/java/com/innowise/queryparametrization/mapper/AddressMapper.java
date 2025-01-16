package com.innowise.queryparametrization.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.innowise.datastarter.mapper.GenericMapper;
import com.innowise.queryparametrization.domain.Address;
import com.innowise.queryparametrization.dto.AddressDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface AddressMapper extends GenericMapper<Address, AddressDto> {

}
