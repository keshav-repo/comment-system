package com.intuit.commentsystem.dto;

import com.intuit.commentsystem.document.ReactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReactionDto {
    private String refId;
    private String actionType;
    private String reactionType;
    private String userId;
}
