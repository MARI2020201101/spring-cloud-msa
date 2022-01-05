package com.mariworld.authservice.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mariworld.authservice.vo.RequestAuth;
import com.mariworld.authservice.vo.ResponseAuth;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Slf4j
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

    @Test
    public void jsonSerializeTest(){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Set<ResponseAuth.Role> set= new HashSet<>();
        set.add(ResponseAuth.Role.ADMIN);
        set.add(ResponseAuth.Role.USER);
        ResponseAuth responseAuth = ResponseAuth.builder()
                .serviceId("first-service")
                .pwd("first-service")
                .roles(set)
                .build();
        String jsonResponseAuth = gson.toJson(responseAuth);
        log.warn(jsonResponseAuth);

    }
}
