package com.intuit.commentsystem.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostReaction extends Reaction{
    private String postId;
    @Builder
    public PostReaction(String reactionId, String userId, LocalDateTime dateTime, ReactionType reactionType, String postId) {
        super(reactionId, userId, dateTime, reactionType);
        this.postId = postId;
    }
}
