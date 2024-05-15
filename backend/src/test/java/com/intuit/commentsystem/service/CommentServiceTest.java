package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Comment;
import com.intuit.commentsystem.document.Post;
import com.intuit.commentsystem.dto.CommentDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ActiveProfiles("test")
@DataMongoTest
@ComponentScan("com.intuit.commentsystem.service")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void cleanUpBeforeOperation() throws IOException {
        mongoTemplate.remove(new Query(), Comment.class);
    }

    @Test
    public void firstlevelcommentTest() throws InterruptedException {
        List<Comment> commentList = populateInMomoryDB();

        List<CommentDto> commentDtoList = commentService.firstlevelcomment(2, "");

        Assertions.assertEquals(2, commentDtoList.size());
        Assertions.assertEquals(commentDtoList.get(0).getCommentId(), commentList.get(3).getCommentId());
        Assertions.assertEquals(commentDtoList.get(1).getCommentId(), commentList.get(2).getCommentId());
    }

    @Test
    public void getReplyTest() throws InterruptedException {
        List<Comment> commentList = populateInMomoryDB();

        List<CommentDto> replyList = commentService.getReply("1");
        Assertions.assertEquals(2, replyList.size());
        Assertions.assertEquals("2", replyList.get(0).getCommentId());
        Assertions.assertEquals("5", replyList.get(1).getCommentId());
    }

    @Test
    public void incrementActionCountTest() throws InterruptedException {
        List<Comment> commentList = populateInMomoryDB();
        String commentid = commentList.get(0).getCommentId();
        commentService.incrementActionCount(ActionType.LIKE, commentList.get(0).getCommentId());
        commentService.incrementActionCount(ActionType.LIKE, commentList.get(0).getCommentId());
        commentService.incrementActionCount(ActionType.LIKE, commentList.get(0).getCommentId());

        commentService.incrementActionCount(ActionType.DISLIKE, commentList.get(0).getCommentId());

        Comment comment = mongoTemplate.findById(commentid, Comment.class);
        Assertions.assertEquals(3, comment.getLikeCount());
        Assertions.assertEquals(1, comment.getDislikeCount());
    }

    @Test
    public void addCommentTest() {
        CommentDto commentDto = CommentDto.builder()
                .content("some comment")
                .build();
        commentDto = commentService.addComment(commentDto);

        Comment comment = mongoTemplate.findById(commentDto.getCommentId(), Comment.class);
        Assertions.assertEquals(comment.getCommentId(), commentDto.getCommentId());
        Assertions.assertEquals(comment.getContent(), commentDto.getContent());
    }

    private List<Comment> populateInMomoryDB() throws InterruptedException {
        Post post = Post.builder()
                .content("post content")
                .build();
        mongoTemplate.save(post);

        Comment comment1 = Comment.builder()
                .commentId("1")
                .postId(post.getPostId())
                .content("level 1 first comment")
                .localDateTime(LocalDateTime.now())
                .build();
        mongoTemplate.save(comment1);
        TimeUnit.SECONDS.sleep(1);
        Comment comment2 = Comment.builder()
                .commentId("2")
                .postId(post.getPostId())
                .content("level 2 comment under first comment")
                .localDateTime(LocalDateTime.now())
                .parentId(comment1.getCommentId())
                .build();
        mongoTemplate.save(comment2);
        TimeUnit.SECONDS.sleep(1);
        Comment comment3 = Comment.builder()
                .commentId("3")
                .postId(post.getPostId())
                .content("level 1 second comment")
                .localDateTime(LocalDateTime.now())
                .build();
        mongoTemplate.save(comment3);
        TimeUnit.SECONDS.sleep(1);
        Comment comment4 = Comment.builder()
                .commentId("4")
                .postId(post.getPostId())
                .content("level 1 third comment")
                .localDateTime(LocalDateTime.now())
                .build();
        mongoTemplate.save(comment4);
        TimeUnit.SECONDS.sleep(1);

        Comment comment5 = Comment.builder()
                .commentId("5")
                .postId(post.getPostId())
                .content("level 2 second comment under first comment")
                .localDateTime(LocalDateTime.now())
                .parentId(comment1.getCommentId())
                .build();
        mongoTemplate.save(comment5);

        return List.of(comment1, comment2, comment3, comment4, comment5);
    }

}
