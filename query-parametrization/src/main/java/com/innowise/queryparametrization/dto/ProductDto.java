package com.innowise.queryparametrization.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record ProductDto(

    String id,

    String name,

    String type,

    List<CategoryDto> category,

    BigDecimal price,

    LocalDateTime createdAt,

    LocalDateTime updatedAt
) {

}
