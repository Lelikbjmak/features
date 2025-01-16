package com.innowise.graphql.testgraphql.controller;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.graphql.testgraphql.dto.UserDto;
import com.innowise.graphql.testgraphql.mapper.UserMapper;
import com.innowise.graphql.testgraphql.service.UserService;
import com.innowise.graphql.testgraphql.validation.Create;
import com.innowise.graphql.testgraphql.validation.Edit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {

  UserService userService;

  UserMapper userMapper;

  @MutationMapping
  public UserDto saveUser(@Argument @Validated(Create.class) UserDto user) {
    var userEntity = userMapper.toEntity(user);
    var savedUser = userService.save(userEntity);
    return userMapper.toDto(savedUser);
  }

  @MutationMapping
  public UserDto editUser(@Argument @Validated(Edit.class) UserDto user) {
    var userEntity = userMapper.toEntity(user);
    var savedUser = userService.save(userEntity);
    return userMapper.toDto(savedUser);
  }

  @QueryMapping("listAllUsers")
  public List<UserDto> findAll() {
    var userList = userService.findAll();
    return userMapper.toDtoList(userList);
  }

  @QueryMapping("findUserById")
  public UserDto findById(@Argument String id) {
    var user = userService.finById(id);
    return userMapper.toDto(user);
  }

  @MutationMapping("deleteUser")
  public void deleteUser(@Argument String id) {
    userService.deleteById(id);
  }
}
