package com.innowise.queryparametrization.reveision1.util;

import com.innowise.queryparametrization.domain.Product;
import com.innowise.queryparametrization.domain.Product.Fields;
import java.time.LocalDateTime;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class ProductSpecificationUtil {

  public static Specification<Product> sequenceIdEquals(String type) {
    return (r, cq, cb) -> cb.equal(r.get(Fields.type),
        type);
  }

  public static Specification<Product> createdAtGreaterThan(LocalDateTime createdAt) {
    return (r, cq, cb) -> cb.greaterThan(r.get(Fields.createdAt),
        createdAt);
  }

  public static Specification<Product> createdAtLessThan(LocalDateTime createdAt) {
    return (r, cq, cb) -> cb.lessThan(r.get(Fields.createdAt),
        createdAt);
  }
}
