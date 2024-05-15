package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.Comment;
import com.intuit.commentsystem.document.Post;
import com.intuit.commentsystem.dto.PostDto;
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
public class PostServiceTest {

    @Autowired
    private PostService postService;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void cleanUpBeforeOperation() throws IOException {
        mongoTemplate.remove(new Query(), Post.class);
    }

    @Test
    public void addPostTest() {
        PostDto postDto = PostDto.builder()
                .postId("1")
                .content("first post")
                .build();
        postDto = postService.addPost(postDto);

        Post post = mongoTemplate.findById(postDto.getPostId(), Post.class);
        Assertions.assertEquals(postDto.getPostId(), postDto.getPostId());
        Assertions.assertEquals(post.getContent(), postDto.getContent());
    }

    @Test
    public void getPostTest() {
        Post expectedPost = Post.builder()
                .postId("1")
                .content("first post")
                .build();
        mongoTemplate.save(expectedPost);

        PostDto actualPost = postService.getPost(expectedPost.getPostId());
        Assertions.assertEquals(expectedPost.getPostId(), actualPost.getPostId());
        Assertions.assertEquals(expectedPost.getContent(), actualPost.getContent());
    }

}
