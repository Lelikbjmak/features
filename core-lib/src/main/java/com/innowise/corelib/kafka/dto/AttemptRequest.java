package com.innowise.corelib.kafka.dto;

import java.util.Map;
import java.util.Optional;

public record AttemptRequest(

        String emailId,

        Optional<Map<String, Object>> payload,

        Optional<Map<String, Object>> attachments
) {
}
