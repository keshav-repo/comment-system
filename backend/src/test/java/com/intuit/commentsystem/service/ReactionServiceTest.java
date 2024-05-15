package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.*;
import com.intuit.commentsystem.dto.ReactionParticipent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@ActiveProfiles("test")
@DataMongoTest
@ComponentScan("com.intuit.commentsystem.service")
public class ReactionServiceTest {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private MongoTemplate mongoTemplate;

    // common object used in all test cases
    private Post post;
    Comment comment;
    User user1, user2;
    Reaction reaction1, reaction2, reaction3;

    @BeforeEach
    public void cleanUpBeforeOperation() throws IOException {
        mongoTemplate.remove(new Query(), Reaction.class);
        mongoTemplate.remove(new Query(), User.class);
        mongoTemplate.remove(new Query(), Comment.class);
        mongoTemplate.remove(new Query(), Post.class);
        setupData();
    }

    @Test
    public void getReactionParticipant() {
        ActionType actionType = ActionType.LIKE;
        ReactionType reactionType = ReactionType.COMMENT;
        String commentId = comment.getCommentId();
        List<ReactionParticipent> reactionParticipentList = reactionService.getReactionParticipant(actionType, reactionType, commentId);

        Assertions.assertEquals(2, reactionParticipentList.size());
        Assertions.assertEquals("user1", reactionParticipentList.get(0).getUserName());
        Assertions.assertEquals("user2", reactionParticipentList.get(1).getUserName());
    }

    @Test
    public void reactTest() {
        Reaction reaction = Reaction.builder()
                .userId(user1.getUserId())
                .refId(comment.getCommentId())
                .dateTime(LocalDateTime.now())
                .reactionType(ReactionType.COMMENT)
                .actionType(ActionType.DISLIKE)
                .build();
        reactionService.react(reaction);

        // check dislike count from the comment
        Comment comment1 = mongoTemplate.findById(comment.getCommentId(), Comment.class);
        Assertions.assertEquals(2, comment1.getDislikeCount());
    }

    public void setupData() {
        post = Post.builder()
                .localDateTime(LocalDateTime.now())
                .content("post content")
                .build();
        mongoTemplate.save(post);

        comment = Comment.builder()
                .content("some content")
                .postId(post.getPostId())
                .build();
        mongoTemplate.save(comment);

        user1 = User.builder().userName("user1").build();
        mongoTemplate.save(user1);
        user2 = User.builder().userName("user2").build();
        mongoTemplate.save(user2);

        reaction1 = Reaction.builder()
                .userId(user1.getUserId())
                .refId(comment.getCommentId())
                .dateTime(LocalDateTime.now())
                .reactionType(ReactionType.COMMENT)
                .actionType(ActionType.LIKE)
                .build();
        reactionService.react(reaction1);

        reaction2 = Reaction.builder()
                .userId(user2.getUserId())
                .refId(comment.getCommentId())
                .dateTime(LocalDateTime.now())
                .reactionType(ReactionType.COMMENT)
                .actionType(ActionType.DISLIKE)
                .build();
        reactionService.react(reaction2);

        reaction3 = Reaction.builder()
                .userId(user2.getUserId())
                .refId(comment.getCommentId())
                .dateTime(LocalDateTime.now())
                .reactionType(ReactionType.COMMENT)
                .actionType(ActionType.LIKE)
                .build();
        reactionService.react(reaction3);
    }
}
