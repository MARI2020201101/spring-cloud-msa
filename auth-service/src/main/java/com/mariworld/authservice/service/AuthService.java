package com.mariworld.authservice.service;

import aj.org.objectweb.asm.TypeReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mariworld.authservice.jpa.AuthRepository;
import com.mariworld.authservice.vo.AuthEntity;
import com.mariworld.authservice.vo.RequestAuth;
import com.mariworld.authservice.vo.ResponseAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Base64Util;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private static String secretKey = "AUTH-SERVICE-SECRET-KEY";
    private final AuthRepository authRepository;

    public ResponseAuth verify(RequestAuth requsetAuth) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        AuthEntity authEntity = authRepository.findByServiceId(requsetAuth.getServiceId());
        String requestPwd = encryptWithBase64(requsetAuth.getPwd());
        if(requestPwd.equals(authEntity.getPwd())){
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

              Set<ResponseAuth.Role> roleSet = gson.fromJson(authEntity.getRoles(), new TypeToken<Set<ResponseAuth.Role>>(){}.getType());
                log.info(roleSet.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            authEntity.getRoles();

            ResponseAuth responseAuth = ResponseAuth.builder()
                    .serviceId(authEntity.getServiceId())
                    .pwd(authEntity.getPwd())
                    .roles(new HashSet<>())
                    .build();
            return responseAuth;
        }else throw new RuntimeException("접근 권한이 없는 서비스 요청입니다.");
    }

    private String encryptWithBase64(String pwd) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] pwdBytes = pwd.getBytes();
        return Base64Utils.encodeToString(pwdBytes);
    }

    private byte[] encryptWithAES(String pwd) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] pwdBytes = pwd.getBytes();
        byte[] secretKeyBytes = Arrays.copyOf(secretKey.getBytes(),16);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] resultPwd = cipher.doFinal(pwdBytes);
        return resultPwd;
    }
}
