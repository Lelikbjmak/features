package com.innowise.queryparametrization.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.innowise.datastarter.mapper.GenericMapper;
import com.innowise.queryparametrization.domain.Person;
import com.innowise.queryparametrization.dto.PersonDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface PersonMapper extends GenericMapper<Person, PersonDto> {

}
