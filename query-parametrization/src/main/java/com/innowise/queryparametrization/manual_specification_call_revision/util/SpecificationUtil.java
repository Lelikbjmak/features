package com.innowise.queryparametrization.manual_specification_call_revision.util;

import java.util.function.Function;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;

@UtilityClass
public class SpecificationUtil {

  // Add spec for each field
  public static <F, S> Specification<S> addFieldSpec(F field, Specification<S> spec,
      Function<F, Specification<S>> function) {
    if (field != null) {
      if (spec != null) {
        return spec.and(function.apply(field));
      } else {
        return function.apply(field);
      }
    }
    return spec;
  }
}
