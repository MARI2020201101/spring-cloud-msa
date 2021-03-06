package com.mariworld.userservice.vo;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String pwd;
    private String userId;
    private String encryptedPwd;
    @ColumnDefault(value = "CURRENT_TIMESTAMP")
    private Date createdAt;
}
