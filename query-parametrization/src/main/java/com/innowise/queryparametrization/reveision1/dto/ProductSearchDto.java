package com.innowise.queryparametrization.reveision1.dto;


import static com.innowise.queryparametrization.reveision1.enumeration.SearchOperation.EQUAL;
import static com.innowise.queryparametrization.reveision1.enumeration.SearchOperation.GREATER_THAN;
import static com.innowise.queryparametrization.reveision1.enumeration.SearchOperation.LESS_THAN;
import static lombok.AccessLevel.PRIVATE;

import com.innowise.queryparametrization.domain.Product.Fields;
import com.innowise.queryparametrization.reveision1.annotation.Searchable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
// TODO: violate SOLID - S - genereate spec + fetch data
public class ProductSearchDto {

  @Searchable(field = Fields.type, operation = EQUAL)
  String type;

  @Searchable(field = Fields.type, operation = EQUAL)
  BigDecimal price;

  @Searchable(field = Fields.type, operation = GREATER_THAN)
  BigDecimal minPrice;

  @Searchable(field = Fields.type, operation = LESS_THAN)
  BigDecimal maxPrice;

  @Searchable(field = Fields.createdAt, operation = EQUAL)
  LocalDateTime createdAt;

  @Searchable(field = Fields.createdAt, operation = GREATER_THAN)
  LocalDateTime createdAfter;

  @Searchable(field = Fields.createdAt, operation = LESS_THAN)
  LocalDateTime createdBefore;
}
