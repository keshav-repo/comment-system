package com.intuit.commentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Post {
    @Id
    private String postId;
    private String content;
    private LocalDateTime localDateTime;
    @BsonIgnore
    private List<Reaction> likesList;
    @BsonIgnore
    private List<Reaction> dislikeList;
}
