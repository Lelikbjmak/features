package com.innowise.queryparametrization;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.junit.jupiter.api.Test;

public class Testss {

    @Test
    void a() {
        OffsetDateTime odt = OffsetDateTime.parse("2014-08-09T18:31:42Z");
        OffsetDateTime a = odt.withOffsetSameInstant(ZoneOffset.UTC);
        System.out.println(a.toLocalDateTime());
        LocalDateTime b = a.toLocalDateTime();
        System.out.println(OffsetDateTime.of(b, ZoneOffset.UTC));
    }
}
