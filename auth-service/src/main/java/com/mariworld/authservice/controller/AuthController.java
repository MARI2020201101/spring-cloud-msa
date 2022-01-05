package com.mariworld.authservice.controller;

import com.mariworld.authservice.service.AuthService;
import com.mariworld.authservice.vo.RequestAuth;
import com.mariworld.authservice.vo.ResponseAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@Slf4j
@RequestMapping("/auth-service")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    @PostMapping("/verify")
    public ResponseEntity<ResponseAuth> verify(@RequestBody RequestAuth requestAuth) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        authService.verify(requestAuth);


        return null;
    }
}
