package com.mariworld.userservice.controller;

import com.mariworld.userservice.vo.Greeting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UserController {

    private Environment env;
    @Autowired
    private Greeting greeting;

    public UserController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health_check")
    public String healthCheck(){
        return "health check in working user service!";
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage();
    }
}
