package com.innowise.queryparametrization.specification;

import com.innowise.queryparametrization.dto_specfication_strategy.operation.FilterOperation;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import lombok.experimental.UtilityClass;

@UtilityClass
public class SpecificationUtil {

    public static <T> Predicate build(
            CriteriaBuilder criteriaBuilder, Path<T> completeAttributePath,
            FilterOperation filterOperation, Object applicableFilterableAttributeValue) {
        if (filterOperation == FilterOperation.GREATER_THAN || filterOperation == FilterOperation.LESS_THAN) {
            if (Comparable.class.isAssignableFrom(completeAttributePath.getJavaType())) {
                @SuppressWarnings({"unchecked"})
                Path<Comparable> filtrationAttributePath = (Path<Comparable>) completeAttributePath;
                Comparable comparableValue = (Comparable) applicableFilterableAttributeValue;

                return switch (filterOperation) {
                    case GREATER_THAN -> criteriaBuilder.greaterThan(
                             filtrationAttributePath,
                            comparableValue);
                    case LESS_THAN -> criteriaBuilder.lessThan(
                            filtrationAttributePath,
                            criteriaBuilder.literal(comparableValue));
                    default -> throw new IllegalArgumentException("Unexpected FilterOperation: " +
                            filterOperation + " during building Predicate for Dynamic specifications.");
                };
            } else {
                throw new IllegalArgumentException(
                        "Path type must be Comparable for operation " + filterOperation);
            }
        } else {
            return switch (filterOperation) {
                case NOT_EQUAL -> criteriaBuilder.notEqual(completeAttributePath, applicableFilterableAttributeValue);
                case EQUAL -> criteriaBuilder.equal(completeAttributePath, applicableFilterableAttributeValue);
                default -> throw new IllegalArgumentException("Unexpected FilterOperation: " +
                        filterOperation + " during building Predicate for Dynamic specifications.");
            };
        }
    }
}
