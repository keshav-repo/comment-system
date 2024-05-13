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
    private String userName;
    private ReactionType reactionType;
}
