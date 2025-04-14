package com.study.user_service.service;

import com.google.common.collect.Lists;
import com.study.user_service.dto.UserDto;
import com.study.user_service.entity.UserEntity;
import com.study.user_service.repository.UserRepository;
import com.study.user_service.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.create(passwordEncoder.encode(userDto.getPassword()));

        UserEntity userEntity = userRepository.save(mapper.map(userDto, UserEntity.class));

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserById(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found."));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(),
                true, true, true, true,
                Lists.newArrayList());
    }
}
