package com.intuit.commentsystem.service;

import java.util.List;
import com.intuit.commentsystem.dto.CommentDto;

public interface CommentService {
    /**
     * Get first n first lavel comments
     * @param n
     * @return List<CommentResponse>
     */
    public List<CommentDto> firstlevelcomment(int n);

    /**
     * return a list of comment corresponding to reply for a comment
     * @param commentId
     * @return List<CommentResponse>
     */
    public List<CommentDto> reply(String commentId);

    /**
     * Add a comment
     * @param commentDto
     */
    public void addComment(CommentDto commentDto);
}
