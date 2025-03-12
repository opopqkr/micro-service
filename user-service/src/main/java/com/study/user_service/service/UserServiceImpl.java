package com.study.user_service.service;

import com.study.user_service.dto.UserDto;
import com.study.user_service.entity.UserEntity;
import com.study.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.create(passwordEncoder.encode(userDto.getPassword()));

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = userRepository.save(mapper.map(userDto, UserEntity.class));

        return mapper.map(userEntity, UserDto.class);
    }

}
