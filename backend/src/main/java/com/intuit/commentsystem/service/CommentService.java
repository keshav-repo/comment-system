package com.intuit.commentsystem.service;

import java.util.List;

import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Comment;
import com.intuit.commentsystem.dto.CommentDto;

public interface CommentService {
    /**
     * Get first n first lavel comments
     * @param n
     * @return List<CommentResponse>
     */
    public List<CommentDto> firstlevelcomment(int n, String postId);

    /**
     * return a list of comment corresponding to reply for a comment
     * @param commentId
     * @return List<CommentResponse>
     */
    public List<CommentDto> getReply(String commentId);

    /**
     * Add a comment
     * @param commentDto
     */
    public CommentDto addComment(CommentDto commentDto);

    /**
     * get comment corresponding to a commentIt
     * @param commentId
     */
    public CommentDto getComment(String commentId);

    /**
     * increase like or dislike count
     * @param actionType
     * @param commentId
     */
    public void incrementActionCount(ActionType actionType, String commentId);
}
