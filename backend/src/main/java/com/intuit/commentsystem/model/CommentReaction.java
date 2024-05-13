package com.intuit.commentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentReaction extends Reaction{
    private String commentId;

    @Builder
    public CommentReaction(String reactionId, String userId, LocalDateTime dateTime, ReactionType reactionType, String commentId) {
        super(reactionId, userId, dateTime, reactionType);
        this.commentId = commentId;
    }
}
