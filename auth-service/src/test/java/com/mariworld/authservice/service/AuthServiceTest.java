package com.mariworld.authservice.service;

import com.mariworld.authservice.vo.RequestAuth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class AuthServiceTest {

    @Autowired
    private AuthService authService;

//    @Test
//    public void encryptTest() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
//        authService.encryptPwd("first-service");
//    }
//    @Test
//    public void encryptTestV2() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
//        authService.encryptPwdV2("first-service");
//    }

    @Test
    public void verifyTest() throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        RequestAuth requestAuth = new RequestAuth();
        requestAuth.setServiceId("first-service");
        requestAuth.setPwd("first-service");
        authService.verify(requestAuth);
    }
}
