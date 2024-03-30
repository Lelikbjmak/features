package com.innowise.dynamicspecification.specification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@NoArgsConstructor
public class DynamicSpecificationContext<T> {

    private Specification<T> specification;
}
