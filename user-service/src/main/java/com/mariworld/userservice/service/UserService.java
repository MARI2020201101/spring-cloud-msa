package com.mariworld.userservice.service;

import com.mariworld.userservice.vo.UserDto;
import com.mariworld.userservice.vo.UserEntity;

public interface UserService {

    UserDto createUser(UserDto userDto);
}
