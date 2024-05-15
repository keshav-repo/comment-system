package com.intuit.commentsystem.controller;

import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.UserDto;
import com.intuit.commentsystem.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping
    public UserDto addUser(@RequestBody UserDto userDto) {
        return userService.addUser(User.builder()
                .userName(userDto.getUserName())
                .build());
    }
    @GetMapping
    public UserDto addUser(@RequestParam String userName) {
        log.info("received request to fetch user");
        return userService.getUser(userName);
    }
}
