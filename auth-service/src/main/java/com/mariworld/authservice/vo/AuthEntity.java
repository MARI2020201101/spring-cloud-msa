package com.mariworld.authservice.vo;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="auth")
public class AuthEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String serviceId;
    private String pwd;
    @Lob
    private String roles;
}
