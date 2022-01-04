package com.mariworld.authservice.service;

import com.mariworld.authservice.jpa.AuthRepository;
import com.mariworld.authservice.vo.RequestAuth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private static String secretKey = "AUTH-SERVICE-SECRET-KEY";
    private final AuthRepository authRepository;

    public void verify(RequestAuth requsetAuth){
        authRepository.findByServiceId(requsetAuth.getServiceId())
    }

    private byte[] encryptPwd(String pwd) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        byte[] pwdBytes = pwd.getBytes();
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(pwdBytes);
        String pw = "";
        for (byte b : encode) {
            Character aChar =(char) b;
            pw+=aChar;
        }
        log.info(pw);
        return encode;
    }

    private byte[] encryptPwdV2(String pwd) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] pwdBytes = pwd.getBytes();
        byte[] secretKeyBytes = secretKey.substring(0,16).getBytes();
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, "AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] resultPwd = cipher.doFinal(pwdBytes);
        return resultPwd;
    }
}
