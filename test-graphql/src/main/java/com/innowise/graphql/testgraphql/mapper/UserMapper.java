package com.innowise.graphql.testgraphql.mapper;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

import com.innowise.graphql.testgraphql.dto.UserDto;
import com.innowise.graphql.testgraphql.entity.User;
import java.util.List;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = SPRING, uses = RoleMapper.class)
public interface UserMapper {

  UserDto toDto(User entity);

  User toEntity(UserDto dto);

  @BeanMapping(nullValuePropertyMappingStrategy = IGNORE)
  User edit(User user, @MappingTarget User editableUser);

  List<UserDto> toDtoList(List<User> entities);
}
