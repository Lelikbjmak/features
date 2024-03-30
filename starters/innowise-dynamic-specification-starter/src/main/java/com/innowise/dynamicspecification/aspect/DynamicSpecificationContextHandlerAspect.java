package com.innowise.dynamicspecification.aspect;

import com.innowise.dynamicspecification.specification.DynamicSpecificationContext;
import com.innowise.dynamicspecification.service.DynamicSpecificationBuilder;
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
@SuppressWarnings({"unchecked", "rawtypes"})
public class DynamicSpecificationContextHandlerAspect {

  @Autowired
  private DynamicSpecificationContext specificationContext;

  @Autowired
  private DynamicSpecificationBuilder specificationService;

  @Pointcut("@annotation(com.innowise.dynamicspecification.annotation.GeneratingDynamicSpecification)")
  public void filtrationMethodsPointcut() {

  }

  @Before("filtrationMethodsPointcut()")
  public void beforeRequest(JoinPoint joinPoint) {
    MultiValueMap<String, Object> filters = getFiltersFromArguments(joinPoint.getArgs());

    Specification<?> specification = specificationService.build(filters);
    specificationContext.setSpecification(specification);
  }

  private MultiValueMap<String, Object> getFiltersFromArguments(Object[] args) {
    for (Object arg : args) {
      if (arg instanceof MultiValueMap) {
        return (MultiValueMap<String, Object>) arg;
      }
    }
    throw new IllegalArgumentException(
        "No MultiValueMap argument found in method annotated with @Filterable");
  }
}
