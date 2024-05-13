package com.intuit.commentsystem.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("comment")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    private String commentId;
    private String content;
    private String parentId;
    private LocalDateTime localDateTime;
    @Indexed
    private String postId;

    @BsonIgnore
    private List<Reaction> likes;
    @BsonIgnore
    private List<Reaction> dislikes;
}
