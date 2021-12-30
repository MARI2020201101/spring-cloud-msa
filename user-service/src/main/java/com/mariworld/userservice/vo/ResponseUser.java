package com.mariworld.userservice.vo;

import lombok.Data;
import lombok.NonNull;

@Data
public class ResponseUser {

    private String name;
    private String pwd;
    private String userId;
}
