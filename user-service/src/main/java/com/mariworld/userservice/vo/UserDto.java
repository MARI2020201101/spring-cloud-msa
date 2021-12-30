package com.mariworld.userservice.vo;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private String name;
    private String email;
    private String pwd;
    private String userId;
    private Date createdAt;
    private String encryptedPwd;
}
