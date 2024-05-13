package com.intuit.commentsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private String content;
    List<ReactionDto> likes;
    List<ReactionDto> dislikes;
}
