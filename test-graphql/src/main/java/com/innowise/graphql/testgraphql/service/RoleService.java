package com.innowise.graphql.testgraphql.service;

import com.innowise.graphql.testgraphql.entity.Role;
import java.util.UUID;

public interface RoleService {

  Role save(Role role);

  Role findById(UUID id);

  Role findByName(String name);
}
