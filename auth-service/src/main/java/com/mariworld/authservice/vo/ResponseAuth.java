package com.mariworld.authservice.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class ResponseAuth implements Serializable {
    private String serviceId;
    private String pwd;
    private Set<Role> roles = new HashSet<>();


    public static enum Role{
        USER, ADMIN;
    }
}
