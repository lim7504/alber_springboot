package org.themselves.alber.controller.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserEmailDto {
    @NotEmpty
    private String email;
}
