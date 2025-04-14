package com.study.user_service.controller;

import com.study.user_service.dto.UserDto;
import com.study.user_service.service.UserService;
import com.study.user_service.vo.Greeting;
import com.study.user_service.vo.RequestUser;
import com.study.user_service.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class UserController {

    private final Environment env;

    private final Greeting greeting;

    private final UserService userService;

    private final ModelMapper mapper;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's Working in User Service on PORT %s",
                env.getProperty("local.server.port"));
    }

    @GetMapping("/welcome")
    public String welcome() {
        // return env.getProperty("greeting.message");
        return greeting.getMessage();
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto createdUserDto = userService.createUser(userDto);

        ResponseUser responseUser = mapper.map(createdUserDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId) {
        UserDto userDto = userService.getUserById(userId);

        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.ok(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserDto> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(user -> {
            result.add(mapper.map(user, ResponseUser.class));
        });

        return ResponseEntity.ok(result);
    }

}
