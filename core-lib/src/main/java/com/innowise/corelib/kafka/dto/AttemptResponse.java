package com.innowise.corelib.kafka.dto;

import com.innowise.corelib.kafka.dto.enumerations.Status;

public record AttemptResponse(

        String attemptId,

        String mailId,

        int version,

        Status attemptStatus

) {
}
