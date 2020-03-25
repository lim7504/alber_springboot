package org.themselves.alber.controller.user.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserPasswordDto {
    @NotEmpty
    private String password;
}
