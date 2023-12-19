package com.innowise.corelib.kafka.dto;

import com.innowise.corelib.kafka.dto.enumerations.EmailType;

import java.util.Map;

public record EmailRequest(

        String email,

        EmailType type,

        Map<String, Object> payload,

        Map<String, String> attachments
) {
}
