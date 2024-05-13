package com.intuit.commentsystem.service.impl;

import com.intuit.commentsystem.document.Post;
import com.intuit.commentsystem.dto.PostDto;
import com.intuit.commentsystem.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
public class PostServiceImpl implements PostService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public Post addPost(PostDto postDto) {
        Post post = Post.builder()
                .content(postDto.getContent())
                .localDateTime(LocalDateTime.now())
                .build();
        try {
            mongoTemplate.save(post);
        } catch (Exception e) {
            log.error("unable to save post info");
        }
        return post;
    }

    @Override
    public Post getPost(String postId) {
        Post post = null;
        Query query = Query.query(Criteria.where("_id").is(postId));
        try{
          post = mongoTemplate.findOne(query, Post.class);
        }catch (Exception e){
            log.error("error finding post {}", postId);
        }
        return post;
    }
}
