package com.mariworld.userservice.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class UserDto {

    private String name;
    private String email;
    private String pwd;
    private String userId;
    private Date createdAt;
    private String encryptedPwd;

    private List<ResponseOrder> orders = new ArrayList<>();
}
