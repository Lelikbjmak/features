package com.innowise.queryparametrization.specification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.ManagedType;
import jakarta.persistence.metamodel.PluralAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SpecificationService {

    private final EntityManager entityManager;

    public <T> Specification<T> generateSpecification(MultiValueMap<String, Object> filters) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            filters.forEach((rawFilterableAttributePath, applicableFilterableAttributeValue) -> {
                String[] rawFilterableAttributeSegments = rawFilterableAttributePath.split("\\.");
                Pair<Path<?>, String> attributePathAndFilterOperationTicket = buildCompletePath(rawFilterableAttributeSegments, root);

                var filterOperation = FilterOperationUtil.operation(attributePathAndFilterOperationTicket.getSecond());
                if (filterOperation.isPresent()) {
                    if (applicableFilterableAttributeValue.size() > 1) {
                        List<Predicate> orPredicates = new ArrayList<>();
                        for (Object value : applicableFilterableAttributeValue) {
                            Object convertedValue = convertValueIfNeeded((attributePathAndFilterOperationTicket.getFirst()).getJavaType(), value);
                            orPredicates.add(SpecificationUtil.build(cb, attributePathAndFilterOperationTicket.getFirst(), filterOperation.get(), convertedValue));
                        }
                        predicates.add(cb.or(orPredicates.toArray(new Predicate[0])));
                    } else {
                        Object value = applicableFilterableAttributeValue.get(0);
                        Object convertedValue = convertValueIfNeeded((attributePathAndFilterOperationTicket.getFirst()).getJavaType(), value);
                        predicates.add(SpecificationUtil.build(cb, attributePathAndFilterOperationTicket.getFirst(), filterOperation.get(), convertedValue));
                    }
                }
            });

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }

    private Pair<Path<?>, String> buildCompletePath(String[] attributeSegments, Path<?> filtrationAttributePath) {
        String operationTicket = "";
        for (String segment : attributeSegments) {
            String part = segment;
            if (part.contains("[")) {
                operationTicket = FilterOperationUtil.ticket(part);
                part = part.substring(0, part.indexOf('['));
            }
            if (!doesAttributeExist(filtrationAttributePath.getJavaType(), part)) {
                break;
            }
            filtrationAttributePath = joinNestedEntityPath(filtrationAttributePath, part);
        }

        return Pair.of(filtrationAttributePath, operationTicket);
    }

    private boolean doesAttributeExist(Class<?> javaType, String attributeName) {
        ManagedType<?> managedType = entityManager.getMetamodel().managedType(javaType);
        return managedType.getAttributes().stream()
                .anyMatch(attr -> attr.getName().equals(attributeName));
    }

    private Path<?> joinNestedEntityPath(Path<?> filtrationAttributePath, String part) {
        ManagedType<?> managedType = entityManager.getMetamodel().managedType(filtrationAttributePath.getJavaType());
        Attribute<?, ?> attribute = managedType.getAttribute(part);

        if (attribute instanceof SingularAttribute) {
            return filtrationAttributePath.get(part);
        } else if (attribute instanceof PluralAttribute) {
            return ((From<?, ?>) filtrationAttributePath).join(part, JoinType.LEFT);
        } else {
            throw new IllegalArgumentException("Unsupported attribute type for join: " + part);
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
