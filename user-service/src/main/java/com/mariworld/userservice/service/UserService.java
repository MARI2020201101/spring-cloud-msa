package com.mariworld.userservice.service;

import com.mariworld.userservice.vo.UserDto;
import com.mariworld.userservice.vo.UserEntity;

public interface UserService {

    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    UserDto getUserByUserIdV2(String userId);
    UserDto getUserByUserIdException(String userId);
    Iterable<UserEntity> getUserByAll();
}
