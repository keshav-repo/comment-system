package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.Post;
import com.intuit.commentsystem.dto.PostDto;

public interface PostService {
    /**
     * Create a post info
     * @param postDto
     * @return Post
     */
    public Post addPost(PostDto postDto);

    /**
     * get a post
     * @param postId
     * @return Post
     */
    public Post getPost(String postId);
}
