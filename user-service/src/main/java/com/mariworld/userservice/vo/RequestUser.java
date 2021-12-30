package com.mariworld.userservice.vo;

import lombok.Data;
import lombok.NonNull;

@Data
public class RequestUser {

    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String pwd;
}
