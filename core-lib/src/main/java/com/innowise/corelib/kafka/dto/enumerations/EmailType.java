package com.innowise.corelib.kafka.dto.enumerations;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@RequiredArgsConstructor
@Accessors(fluent = true)
public enum EmailType {

    WELCOME("welcome"),

    REPORT("report");

    private final String template;
}
