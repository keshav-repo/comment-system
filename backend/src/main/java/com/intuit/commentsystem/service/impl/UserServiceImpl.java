package com.intuit.commentsystem.service.impl;

import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.UserDto;
import com.intuit.commentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public UserDto addUser(User user) {
        try{
            mongoTemplate.save(user);
        }catch (Exception e){

        }
        return UserDto.builder().userId(user.getUserId())
                .userName(user.getUserName()).build();
    }

    @Override
    public UserDto getUser(String userId) {
        User user = null;
        Query query = Query.query(Criteria.where("userName").is(userId));
        try{
           user = mongoTemplate.findOne(query, User.class);
        }catch (Exception e){

        }
        return UserDto.builder().userId(user.getUserId())
                .userName(user.getUserName()).build();
    }
}
