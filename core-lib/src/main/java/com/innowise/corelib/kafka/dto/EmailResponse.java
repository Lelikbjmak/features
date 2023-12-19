package com.innowise.corelib.kafka.dto;

import com.innowise.corelib.kafka.dto.enumerations.Status;

public record EmailResponse(

        String mailId,

        Status creationStatus
) {
}
