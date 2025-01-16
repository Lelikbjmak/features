package com.innowise.graphql.testgraphql.controller;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.graphql.testgraphql.dto.RoleDto;
import com.innowise.graphql.testgraphql.mapper.RoleMapper;
import com.innowise.graphql.testgraphql.service.RoleService;
import com.innowise.graphql.testgraphql.validation.Create;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleController {

  RoleService roleService;

  RoleMapper roleMapper;

  @MutationMapping
  public RoleDto saveRole(@Argument @Validated(Create.class) RoleDto role) {
    var roleEntity = roleMapper.toEntity(role);
    var savedRole = roleService.save(roleEntity);
    return roleMapper.toDto(savedRole);
  }
}
