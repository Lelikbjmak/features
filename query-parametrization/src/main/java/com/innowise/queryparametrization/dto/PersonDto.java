package com.innowise.queryparametrization.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PersonDto(

        String id,

        String name,

        String type,

        List<CategoryDto> addresses,

        BigDecimal price,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}
