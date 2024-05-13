package com.intuit.commentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String commentId;
    private String content;
    private String parentId;
    private LocalDateTime localDateTime;
    List<ReactionDto> likes;
    List<ReactionDto> dislikes;
}
