package com.innowise.graphql.testgraphql.service;


import com.innowise.graphql.testgraphql.entity.User;
import java.util.List;

public interface UserService {

  User save(User user);

  User edit(User user);

  User findByUsername(String username);

  User findByEmail(String email);

  User finById(String id);

  List<User> findAll();

  void deleteById(String id);
}
