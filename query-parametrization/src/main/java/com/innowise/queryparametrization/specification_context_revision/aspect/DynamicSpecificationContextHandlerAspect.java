package com.innowise.queryparametrization.specification_context_revision.aspect;

import com.innowise.queryparametrization.specification.SpecificationService;
import com.innowise.queryparametrization.specification_context_revision.SpecificationContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

@Aspect
@Component
public class DynamicSpecificationContextHandlerAspect {

    @Autowired
    private SpecificationContext specificationContext;

    @Autowired
    private SpecificationService specificationService;

    @Pointcut("@annotation(com.innowise.queryparametrization.specification_context_revision.annotation.EnableDynamicSpecification)")
    public void filtrationMethodsPointcut() {

    }

    @Before("filtrationMethodsPointcut()")
    public void beforeRequest(JoinPoint joinPoint) {
        MultiValueMap<String, Object> filters = getFiltersFromArguments(joinPoint.getArgs());

        Specification<?> specification = specificationService.generateSpecification(filters);
        specificationContext.setSpecification(specification);
    }

    private MultiValueMap<String, Object> getFiltersFromArguments(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof MultiValueMap) {
                return (MultiValueMap<String, Object>) arg;
            }
        }
        throw new IllegalArgumentException("No MultiValueMap argument found in method annotated with @Filterable");
    }
}
