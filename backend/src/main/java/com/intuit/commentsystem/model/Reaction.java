package com.intuit.commentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("reaction")
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Reaction {
    @Id
    private String reactionId;
    private String userId;
    private LocalDateTime dateTime;
    private ReactionType reactionType;
}
