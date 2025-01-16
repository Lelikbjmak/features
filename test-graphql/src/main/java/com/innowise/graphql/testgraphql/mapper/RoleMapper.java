package com.innowise.graphql.testgraphql.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

import com.innowise.graphql.testgraphql.dto.RoleDto;
import com.innowise.graphql.testgraphql.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = SPRING)
public interface RoleMapper {

  Role toEntity(RoleDto dto);

  RoleDto toDto(Role entity);
}
