package com.innowise.graphql.testgraphql.repository;


import com.innowise.graphql.testgraphql.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, String> {

  @Query(value = "SELECT user from User user where user.username = :username")
  Optional<User> findByUsername(@Param(value = "username") String username);

  @Query(value = "SELECT user FROM User user WHERE user.email = :email")
  Optional<User> findByEmail(@Param(value = "email") String email);
}
