package com.innowise.graphql.testgraphql.dto;

import com.innowise.graphql.testgraphql.validation.Create;
import jakarta.validation.constraints.NotBlank;
import java.util.UUID;

public record RoleDto(

    UUID id,

    @NotBlank(groups = Create.class, message = "Can't be blank")
    String name
) {

}
