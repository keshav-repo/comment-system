package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.UserDto;

public interface UserService {
    /**
     * add a user
     * @param user
     * @return User
     */
    public UserDto addUser(User user);

    /**
     * Get user by userId
     * @param userId
     * @return User
     */
    public UserDto getUser(String userId);
}
