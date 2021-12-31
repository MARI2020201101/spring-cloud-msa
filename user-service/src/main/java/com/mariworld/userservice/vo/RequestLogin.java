package com.mariworld.userservice.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class RequestLogin implements Serializable {
    private String email;
    private String password;

}
