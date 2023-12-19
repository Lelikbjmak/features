package com.innowise.queryparametrization.controller;

import com.innowise.queryparametrization.filter.operation.FilterOperation;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/filters/operations")
public class FilterOperationController {

  @GetMapping
  public List<String> operations() {
    return Arrays.stream(FilterOperation.values())
        .map(FilterOperation::getSlug)
        .toList();
  }
}
