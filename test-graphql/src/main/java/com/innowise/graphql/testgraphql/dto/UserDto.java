package com.innowise.graphql.testgraphql.dto;

import com.innowise.graphql.testgraphql.controller.annotation.RoleExists;
import com.innowise.graphql.testgraphql.validation.Create;
import com.innowise.graphql.testgraphql.validation.Edit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

public record UserDto(

    @NotBlank(groups = Edit.class, message = "Id is mandatory")
    String id,

    @NotBlank(groups = {Create.class, Edit.class}, message = "Username is mandatory")
    String username,

    @NotBlank(groups = {Create.class, Edit.class}, message = "Email is mandatory")
    String email,

    @RoleExists(groups = {Create.class, Edit.class})
    @NotEmpty(groups = {Create.class, Edit.class}, message = "Roles are mandatory")
    List<RoleDto> roles,

    LocalDateTime registrationDate,

    LocalDateTime lastTimeUpdated

) {

}
