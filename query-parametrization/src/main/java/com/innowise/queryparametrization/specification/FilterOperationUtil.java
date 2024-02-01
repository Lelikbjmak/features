package com.innowise.queryparametrization.specification;

import com.innowise.queryparametrization.dto_specfication_strategy.operation.FilterOperation;
import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Optional;

@UtilityClass
public class FilterOperationUtil {
    
    public static String ticket(String rawAttributePath) {
        if (rawAttributePath.contains("[") && rawAttributePath.contains("]")) {
            return rawAttributePath.substring(rawAttributePath.indexOf('[') + 1, rawAttributePath.indexOf(']'));
        }
        return "";
    }

    public static Optional<FilterOperation> operation(String ticket) {
        if (ticket.isBlank()) {
            return Optional.of(FilterOperation.EQUAL);
        }
        return Arrays.stream(FilterOperation.values())
                .filter(it -> it.getSlug().equals(ticket))
                .findFirst();
    }
}
