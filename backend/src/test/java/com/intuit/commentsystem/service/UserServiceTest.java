package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.Post;
import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

@ActiveProfiles("test")
@DataMongoTest
@ComponentScan("com.intuit.commentsystem.service")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void cleanUpBeforeOperation() throws IOException {
        mongoTemplate.remove(new Query(), User.class);
    }

    @Test
    public void addUserTest() {
        User user = User.builder()
                .userName("user2")
                .build();
        UserDto userDto = userService.addUser(user);

        User actual = mongoTemplate.findById(user.getUserId(), User.class);
        Assertions.assertEquals(userDto.getUserId(), actual.getUserId());
        Assertions.assertEquals(userDto.getUserName(), actual.getUserName());
    }

    @Test
    public void getUserTest() {
        User user = User.builder()
                .userName("user2")
                .build();
        user = mongoTemplate.save(user);

        UserDto userDto = userService.getUser(user.getUserName());
        Assertions.assertEquals(user.getUserId(), userDto.getUserId());
        Assertions.assertEquals(user.getUserName(), userDto.getUserName());
    }
}
