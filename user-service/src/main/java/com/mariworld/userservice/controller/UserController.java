package com.mariworld.userservice.controller;

import com.mariworld.userservice.service.UserService;
import com.mariworld.userservice.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user-service")
@Slf4j
public class UserController {

    private Environment env;
    private UserService userService;
    @Autowired
    private Greeting greeting;

    public UserController(Environment env, UserService userService) {
        this.env = env;
        this.userService = userService;
    }

    @GetMapping("/health_check")
    public String healthCheck(){
        return String.format("health check in working user service! : port -> %s \n" +
                        "spring config service message -> %s",
                env.getProperty("local.server.port"),
                env.getProperty("service.message"));
    }

    @GetMapping("/welcome")
    public String welcome(){
//        return env.getProperty("greeting.message");
        return greeting.getMessage() + "\n" + greeting.getServiceMsg();
    }
    
    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        ResponseUser responseUser = mapper.map(createdUser, ResponseUser.class);
        return new ResponseEntity<>(responseUser,HttpStatus.CREATED);
//        return "new user created from user service";

    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUserByUserId(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserId(userId);
        ResponseUser user = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/v2/{userId}")
    public ResponseEntity<ResponseUser> getUserByUserIdV2(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserIdV2(userId);
        ResponseUser user = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users/exception/{userId}")
    public ResponseEntity<ResponseUser> getUserByUserIdException(@PathVariable("userId") String userId){
        UserDto userDto = userService.getUserByUserIdException(userId);
        ResponseUser user = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userEntities = userService.getUserByAll();
        List<ResponseUser> users = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        userEntities.forEach(u-> users.add(mapper.map(u, ResponseUser.class)));
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }
}
