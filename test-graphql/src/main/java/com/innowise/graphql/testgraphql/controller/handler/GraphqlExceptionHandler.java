package com.innowise.graphql.testgraphql.controller.handler;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import jakarta.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

@Component
public class GraphqlExceptionHandler extends DataFetcherExceptionResolverAdapter {

  @Override
  protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment env) {
    if (ex instanceof ConstraintViolationException exception) {
      var violations = extractConstraintsViolations(exception);
      return GraphqlErrorBuilder.newError()
          .message(exception.getMessage())
          .errorType(ErrorType.ValidationError)
          .extensions(violations)
          .build();
    }
    return super.resolveToSingleError(ex, env);
  }

  private Map<String, Object> extractConstraintsViolations(
      ConstraintViolationException exception) {
    var handledErrors = new HashMap<String, Object>();
    exception.getConstraintViolations()
        .forEach(it -> handledErrors.put(it.getPropertyPath().toString(), it.getMessageTemplate()));
    return handledErrors;
  }
}
