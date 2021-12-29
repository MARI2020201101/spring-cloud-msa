package com.mariworld.firstservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FirstServiceController {

    @GetMapping("welcome")
    public String welcome(){
        return "welcome to my first service";
    }
}
