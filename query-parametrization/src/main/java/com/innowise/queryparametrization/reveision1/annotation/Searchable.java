package com.innowise.queryparametrization.reveision1.annotation;

import com.innowise.queryparametrization.reveision1.enumeration.SearchOperation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Searchable {

  String field();

  SearchOperation operation();
}
