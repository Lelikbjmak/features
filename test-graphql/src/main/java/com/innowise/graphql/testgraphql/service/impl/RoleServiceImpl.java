package com.innowise.graphql.testgraphql.service.impl;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.graphql.testgraphql.entity.Role;
import com.innowise.graphql.testgraphql.repository.RoleRepository;
import com.innowise.graphql.testgraphql.service.RoleService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleServiceImpl implements RoleService {

  RoleRepository roleRepository;

  @Override
  @Transactional
  public Role save(Role role) {
    return roleRepository.save(role);
  }

  @Override
  @Transactional(readOnly = true)
  public Role findById(UUID id) {
    return roleRepository.findById(id).orElse(null);
  }

  @Override
  @Transactional(readOnly = true)
  public Role findByName(String name) {
    return roleRepository.findByName(name).orElse(null);
  }
}
