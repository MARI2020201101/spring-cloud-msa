package com.mariworld.userservice.vo;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Data
public class RequestUser {

    @NonNull
    @Size(min=2, message = "name cannot be less than 2 characters")
    private String name;
    @NonNull
    @Email
    private String email;
    @NonNull
    @Size(min=4, message = "pwd cannot be less than 4 characters")
    private String pwd;
}
