package com.intuit.commentsystem.controller;

import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Reaction;
import com.intuit.commentsystem.document.ReactionType;
import com.intuit.commentsystem.dto.CommentDto;
import com.intuit.commentsystem.dto.ReactionDto;
import com.intuit.commentsystem.dto.ReactionParticipent;
import com.intuit.commentsystem.service.CommentService;
import com.intuit.commentsystem.service.ReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private ReactionService reactionService;

    @GetMapping("/firstLevel")
    public List<CommentDto> firstlevelcomment(@RequestParam int n, @RequestParam String postId) {
        return commentService.firstlevelcomment(n, postId);
    }

    @GetMapping("/reply/{commentId}")
    public List<CommentDto> getReplyComment(@PathVariable String commentId) {
        return commentService.getReply(commentId);
    }

    @PostMapping
    public CommentDto addComment(@RequestBody CommentDto commentDto) {
        return commentService.addComment(commentDto);
    }

    @GetMapping
    public CommentDto getComment(@RequestParam String commentId){
        return commentService.getComment(commentId);
    }

    @PostMapping("/react")
    public ResponseEntity<Void> react(@RequestBody ReactionDto reactionDto) {
        Reaction reaction = Reaction.builder()
                .actionType(ActionType.of(reactionDto.getActionType()))
                .reactionType(ReactionType.of(reactionDto.getReactionType()))
                .dateTime(LocalDateTime.now())
                .refId(reactionDto.getRefId())
                .userId(reactionDto.getUserId())
                .build();
        reactionService.react(reaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/participant")
    public List<ReactionParticipent> getReactionParticipiant(@RequestParam String commentId, @RequestParam String actionType){
       return reactionService.getReactionParticipant(ActionType.of(actionType), ReactionType.COMMENT, commentId);
    }
}
