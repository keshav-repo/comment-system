package com.intuit.commentsystem.controller;

import com.intuit.commentsystem.dto.CommentDto;
import com.intuit.commentsystem.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

public class CommentController {
    @Autowired
    private CommentService commentService;

    public List<CommentDto> firstlevelcomment(int n){
        return null;
    }

}
