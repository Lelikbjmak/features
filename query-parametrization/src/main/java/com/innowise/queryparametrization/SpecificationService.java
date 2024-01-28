package com.innowise.queryparametrization;

import com.innowise.queryparametrization.dto_specfication_strategy.operation.FilterOperation;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecificationService {

  private final EntityManager entityManager;

  public <T> Specification<T> generateSpecification(MultiValueMap<String, Object> filters) {
    return (root, query, cb) -> {
      List<Predicate> predicates = new ArrayList<>();

      filters.forEach((key, values) -> {
        String[] parts = key.split("\\.");
        Path<?> path = root;
        String slug = "";

        for (String s : parts) {
          String part = s;
          if (part.contains("[")) {
            slug = extractSlug(part);
            part = part.substring(0, part.indexOf('['));
          }

          if (!attributeExists(path.getJavaType(), part)) {
            return;
          }

          path = joinPath(path, part);
        }

        var operation = getFilterOperation(slug);
        if (operation.isPresent()) {
          if (values.size() > 1) {
            List<Predicate> orPredicates = new ArrayList<>();
            for (Object value : values) {
              Object convertedValue = convertValueIfNeeded(path.getJavaType(), value);
              orPredicates.add(buildPredicate(cb, path, operation.get(), convertedValue));
            }
            predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
          } else {
            Object value = values.get(0);
            Object convertedValue = convertValueIfNeeded(path.getJavaType(), value);
            predicates.add(buildPredicate(cb, path, operation.get(), convertedValue));
          }
        }
      });

      return cb.and(predicates.toArray(new Predicate[0]));
    };
  }

  private String extractSlug(String key) {
    if (key.contains("[") && key.contains("]")) {
      return key.substring(key.indexOf('[') + 1, key.indexOf(']'));
    }
    return "";
  }

  private boolean attributeExists(Class<?> javaType, String attributeName) {
    ManagedType<?> managedType = entityManager.getMetamodel().managedType(javaType);
    return managedType.getAttributes().stream()
        .anyMatch(attr -> attr.getName().equals(attributeName));
  }

  private Path<?> joinPath(Path<?> path, String part) {
    ManagedType<?> managedType = entityManager.getMetamodel().managedType(path.getJavaType());
    Attribute<?, ?> attribute = managedType.getAttribute(part);

    if (attribute instanceof SingularAttribute) {
      return path.get(part);
    } else if (attribute instanceof PluralAttribute) {
      return ((From<?, ?>) path).join(part, JoinType.LEFT);
    } else {
      throw new IllegalArgumentException("Unsupported attribute type for join: " + part);
    }
  }

//  private <T> Path<?> getNestedPath(Root<T> root, String fieldName, String nestedAttribute) {
//    Join<Object, Object> join = root.join(fieldName, JoinType.LEFT);
//    return join.get(nestedAttribute);
//  }
//
//  private static <T> boolean fieldExists(Class<T> clazz, String fieldName) {
//    try {
//      clazz.getDeclaredField(fieldName);
//      return true;
//    } catch (NoSuchFieldException e) {
//      log.error(
//          "No such field " + fieldName + " for specification class: " + clazz.getSimpleName());
//      return false;
//    }
//  }

  private Optional<FilterOperation> getFilterOperation(String slug) {
    if (slug.isBlank()) {
      return Optional.of(FilterOperation.EQUAL);
    }
    return Arrays.stream(FilterOperation.values())
        .filter(it -> it.getSlug().equals(slug))
        .findFirst();
  }

  public static Predicate buildPredicate(CriteriaBuilder cb, Path<?> rawPath,
      FilterOperation operation, Object value) {
    if (operation == FilterOperation.GREATER_THAN || operation == FilterOperation.LESS_THAN) {
      if (Comparable.class.isAssignableFrom(rawPath.getJavaType())) {
        @SuppressWarnings({"unchecked"})
        Path<Comparable> path = (Path<Comparable>) rawPath;
        Comparable comparableValue = (Comparable) value;

        return switch (operation) {
          case GREATER_THAN -> cb.greaterThan(path, comparableValue);
          case LESS_THAN -> cb.lessThan(path, comparableValue);
          default -> throw new IllegalStateException("Unexpected operation: " + operation);
        };
      } else {
        throw new IllegalArgumentException(
            "Path type must be Comparable for operation " + operation);
      }
    } else {
      return switch (operation) {
        case NOT_EQUAL -> cb.notEqual(rawPath, value);
        case EQUAL -> cb.equal(rawPath, value);
        default -> throw new IllegalStateException("Unexpected operation: " + operation);
      };
    }
  }

  private Object convertValueIfNeeded(Class<?> fieldType, Object value) {
    if (value instanceof String stringValue) {
      if (LocalDateTime.class.isAssignableFrom(fieldType)) {
        try {
          return LocalDateTime.parse(stringValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (DateTimeParseException e) {
          try {
            LocalDate date = LocalDate.parse(stringValue, DateTimeFormatter.ISO_LOCAL_DATE);
            return date.atStartOfDay();
          } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException(
                "Invalid date format for LocalDateTime: " + stringValue, ex);
          }
        }
      }
      if (LocalDate.class.isAssignableFrom(fieldType)) {
        try {
          return LocalDate.parse(stringValue, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
          throw new IllegalArgumentException("Invalid date format for LocalDate: " + stringValue,
              e);
        }
      }
    }

    return value;
  }
}
