package com.mariworld.userservice.service;

import com.mariworld.userservice.client.OrderServiceClient;
import com.mariworld.userservice.jpa.UserRepository;
import com.mariworld.userservice.vo.ResponseOrder;
import com.mariworld.userservice.vo.UserDto;
import com.mariworld.userservice.vo.UserEntity;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;
    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        userDto.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userRepository.save(userEntity);
        UserDto returnedUser = mapper.map(userEntity, UserDto.class);

        return returnedUser;

    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null) throw new UsernameNotFoundException("non exist user...");
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
//        String url = env.getProperty("url.order-service");
//        String orderUrl = url.replace("%s", userId);
        String orderUrl = String.format(env.getProperty("url.order-service"), userId);
        ResponseEntity<List<ResponseOrder>> orders = restTemplate.exchange(orderUrl, HttpMethod.GET, null
                , new ParameterizedTypeReference<List<ResponseOrder>>() {
                });
        userDto.setOrders(orders.getBody());
        return userDto;
    }

    @Override
    public UserDto getUserByUserIdV2(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null) throw new UsernameNotFoundException("non exist user...");
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public UserDto getUserByUserIdException(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);
        if(userEntity==null) throw new UsernameNotFoundException("non exist user...");
        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        List<ResponseOrder> orders = null;
//        try{
            orders = orderServiceClient.getOrdersException(userId);
//        }catch(FeignException e){
//            e.printStackTrace();
//        }

        userDto.setOrders(orders);
        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }
}
