package com.study.user_service.service;

import com.google.common.collect.Lists;
import com.study.user_service.dto.UserDto;
import com.study.user_service.entity.UserEntity;
import com.study.user_service.repository.UserRepository;
import com.study.user_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final ModelMapper mapper;

    private final RestTemplate restTemplate;

    private final Environment environment;

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
        String orderUrl = String.format(environment.getProperty("order-service.url"), userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<>() {
                });

        List<ResponseOrder> orders = orderListResponse.getBody();
        userDto.setOrders(orders);

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
