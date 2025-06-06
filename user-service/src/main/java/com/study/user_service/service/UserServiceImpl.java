package com.study.user_service.service;

import com.google.common.collect.Lists;
import com.study.user_service.client.OrderServiceClient;
import com.study.user_service.dto.UserDto;
import com.study.user_service.entity.UserEntity;
import com.study.user_service.repository.UserRepository;
import com.study.user_service.vo.ResponseOrder;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final Environment environment;

    private final ModelMapper mapper;

    private final RestTemplate restTemplate;

    private final OrderServiceClient orderServiceClient;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.create(passwordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = userRepository.save(mapper.map(userDto, UserEntity.class));

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        /* Using as restTemplate */
        // String orderUrl = String.format(environment.getProperty("order-service.url"), userId);
        // ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
        //         new ParameterizedTypeReference<>() {
        //         });
        // List<ResponseOrder> orderList = orderListResponse.getBody();

        /* Using a feign client */
        /* 1. FeignException Handling with try-catch */
        // List<ResponseOrder> orderList = null;
        // try {
        //     /* HttpStatus 404 Not found 에러가 발생하더라도,
        //      * FeignException throw 하기 때문에 해당 메소드를 호출하는 client 에게는 500 에러 전달.
        //      * FeignException extends RuntimeException */
        //     orderList = orderServiceClient.getOrders(userId);
        // } catch (FeignException ex) {
        //     log.error(ex.getMessage());
        // }

        /* 2. FeignException Handling using FeignErrorDecoder, which overrides openfeign's ErrorDecoder. */
        List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
        userDto.setOrders(orderList);

        return userDto;
    }

    @Override
    public Iterable<UserDto> getUserByAll() {
        return StreamSupport.stream(
                        Spliterators.spliteratorUnknownSize(userRepository.findAll().iterator(), Spliterator.ORDERED),
                        false)
                .map(e -> mapper.map(e, UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = findByEmail(email);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = findByEmail(username);

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                Lists.newArrayList());
    }

    private UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
