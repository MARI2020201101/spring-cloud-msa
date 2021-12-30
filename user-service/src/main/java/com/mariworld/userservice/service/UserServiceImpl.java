package com.mariworld.userservice.service;

import com.mariworld.userservice.jpa.UserRepository;
import com.mariworld.userservice.vo.UserDto;
import com.mariworld.userservice.vo.UserEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd("encrypted_password");
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);
        UserDto returneduser = mapper.map(userEntity, UserDto.class);

        return returneduser;

    }
}
