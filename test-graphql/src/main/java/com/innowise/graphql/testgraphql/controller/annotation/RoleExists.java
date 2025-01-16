package com.innowise.graphql.testgraphql.controller.annotation;

import com.innowise.graphql.testgraphql.controller.annotation.handler.RoleExistsAnnotationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RoleExistsAnnotationValidator.class)
public @interface RoleExists {

  String message() default "Role does not exist";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
