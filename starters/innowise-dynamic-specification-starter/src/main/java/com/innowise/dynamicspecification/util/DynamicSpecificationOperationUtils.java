package com.innowise.dynamicspecification.util;

import com.innowise.dynamicspecification.specification.DynamicSpecificationOperation;
import java.util.Arrays;
import java.util.Optional;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DynamicSpecificationOperationUtils {
    
    public static String ticket(String rawAttributePath) {
        if (rawAttributePath.contains("[") && rawAttributePath.contains("]")) {
            return rawAttributePath.substring(rawAttributePath.indexOf('[') + 1, rawAttributePath.indexOf(']'));
        }
        return "";
    }

    public static Optional<DynamicSpecificationOperation> operation(String ticket) {
        if (ticket.isBlank()) {
            return Optional.of(DynamicSpecificationOperation.EQUAL);
        }
        return Arrays.stream(DynamicSpecificationOperation.values())
                .filter(it -> it.getSlug().equals(ticket))
                .findFirst();
    }
}
