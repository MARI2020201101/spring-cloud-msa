package com.mariworld.authservice.controller;

import com.mariworld.authservice.service.AuthService;
import com.mariworld.authservice.vo.RequestAuth;
import com.mariworld.authservice.vo.ResponseAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
@RequestMapping("/auth-service")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @GetMapping("/verify")
    public ResponseEntity<ResponseAuth> verify(@RequestBody RequestAuth requestAuth){

    }
}
