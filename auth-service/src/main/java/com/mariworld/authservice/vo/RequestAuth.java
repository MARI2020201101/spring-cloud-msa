package com.mariworld.authservice.vo;

import lombok.Data;

@Data
public class RequestAuth {
    private String serviceId;
    private String pwd;
}
