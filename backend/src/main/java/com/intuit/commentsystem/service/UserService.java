package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.User;

public interface UserService {
    /**
     * add a user
     * @param user
     * @return User
     */
    public User addUser(User user);

    /**
     * Get user by userId
     * @param userId
     * @return User
     */
    public User getUser(String userId);
}
