package com.innowise.graphql.testgraphql.repository;


import com.innowise.graphql.testgraphql.entity.Role;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoleRepository extends JpaRepository<Role, UUID> {

  @Query(value = "SELECT role FROM Role role WHERE role.name = :name")
  Optional<Role> findByName(@Param(value = "name") String name);

  long countAllByNameIn(List<String> names);

}
