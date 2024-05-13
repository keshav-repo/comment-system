package com.intuit.commentsystem.controller;

import com.intuit.commentsystem.document.Post;
import com.intuit.commentsystem.dto.PostDto;
import com.intuit.commentsystem.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    @Autowired
    private PostService postService;
    @PostMapping
    public Post addPost(@RequestBody PostDto postDto){
       return postService.addPost(postDto);
    }
    @GetMapping("/{postId}")
    public Post getPost(@PathVariable String postId){
        return postService.getPost(postId);
    }
}
