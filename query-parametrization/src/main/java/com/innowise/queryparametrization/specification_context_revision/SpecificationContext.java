package com.innowise.queryparametrization.specification_context_revision;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@NoArgsConstructor
public class SpecificationContext<T> {

    private Specification<T> specification;
}
