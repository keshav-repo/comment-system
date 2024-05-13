package com.intuit.commentsystem.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("reaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Reaction {
    @Id
    private String reactionId;
    private String userId;
    private LocalDateTime dateTime;
    private ReactionType reactionType;
    private ActionType actionType;
    // corresponds to commentId, postId or any other
    @Indexed
    private String refId;
}
