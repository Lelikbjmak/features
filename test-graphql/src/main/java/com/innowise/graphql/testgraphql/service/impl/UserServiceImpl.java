package com.innowise.graphql.testgraphql.service.impl;


import static lombok.AccessLevel.PRIVATE;

import com.innowise.graphql.testgraphql.entity.Role;
import com.innowise.graphql.testgraphql.entity.User;
import com.innowise.graphql.testgraphql.mapper.UserMapper;
import com.innowise.graphql.testgraphql.repository.RoleRepository;
import com.innowise.graphql.testgraphql.repository.UserRepository;
import com.innowise.graphql.testgraphql.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.SplittableRandom;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

  UserRepository userRepository;
  RoleRepository roleRepository;

  UserMapper userMapper;

  @Override
  @Transactional
  public User save(User user) {
    var mappedRoles = user.getRoles().stream()
        .map(Role::getName)
        .map(roleRepository::findByName)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList();
    user.setRoles(mappedRoles);
    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User edit(User user) {
    var editableUser = userRepository.findById(user.getId())
        .orElseThrow(EntityNotFoundException::new);
    userMapper.edit(user, editableUser);
    return save(user);
  }

  @Override
  @Transactional(readOnly = true)
  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public User finById(String id) {
    return userRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  @Transactional
  public void deleteById(String id) {
    userRepository.deleteById(id);
  }
}
