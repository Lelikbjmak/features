package com.innowise.graphql.testgraphql.controller.annotation.handler;

import static lombok.AccessLevel.PRIVATE;

import com.innowise.graphql.testgraphql.controller.annotation.RoleExists;
import com.innowise.graphql.testgraphql.dto.RoleDto;
import com.innowise.graphql.testgraphql.repository.RoleRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class RoleExistsAnnotationValidator implements
    ConstraintValidator<RoleExists, List<RoleDto>> {

  RoleRepository roleRepository;

  @Override
  public boolean isValid(List<RoleDto> value, ConstraintValidatorContext context) {
    if (value == null) {
      return false;
    }

    var names = value.stream()
        .filter(Objects::nonNull)
        .map(RoleDto::name)
        .filter(it -> !it.isBlank())
        .distinct()
        .toList();

    var doesAnyRoleNotExist = names.stream()
        .map(name -> {
          var possibleRole = roleRepository.findByName(name);
          if (possibleRole.isEmpty()) {
            context.buildConstraintViolationWithTemplate("Role " + name + " does not exist")
                .addConstraintViolation();
          }
          return possibleRole;
        })
        .anyMatch(Optional::isEmpty);

    return !doesAnyRoleNotExist;
  }
}
