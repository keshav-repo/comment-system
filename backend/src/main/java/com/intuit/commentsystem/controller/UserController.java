package com.intuit.commentsystem.controller;

import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.UserDto;
import com.intuit.commentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
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
        return userService.getUser(userName);
    }
}
