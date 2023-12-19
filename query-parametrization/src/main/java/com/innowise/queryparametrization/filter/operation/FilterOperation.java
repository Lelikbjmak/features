package com.innowise.queryparametrization.filter.operation;

import static lombok.AccessLevel.PRIVATE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Getter
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public enum FilterOperation {

  GREATER_THAN("gt"),

  LESS_THAN("lt"),

  NOT_EQUAL("neq"),

  EQUAL("eq");

  String slug;
}
