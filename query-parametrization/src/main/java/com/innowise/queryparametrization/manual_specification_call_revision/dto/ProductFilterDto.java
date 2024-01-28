package com.innowise.queryparametrization.manual_specification_call_revision.dto;

import com.innowise.queryparametrization.dto.CategoryDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record ProductFilterDto(

        String id,

        String name,

        String type,

        List<CategoryDto> categories,

        BigDecimal price,

        BigDecimal maxPrice,

        BigDecimal minPrice,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {

}
