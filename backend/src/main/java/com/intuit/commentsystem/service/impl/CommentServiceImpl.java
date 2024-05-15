package com.intuit.commentsystem.service.impl;

import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Comment;
import com.intuit.commentsystem.dto.CommentDto;
import com.intuit.commentsystem.service.CommentService;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<CommentDto> firstlevelcomment(int n, String postId) {
        Query query = new Query(( Criteria.where("postId").is(postId).and("parentId").isNull()));
        query.with(Sort.by(new Sort.Order(Sort.Direction.DESC, "localDateTime")));
        query.limit(n);
        List<Comment> comments = null;
        try {
            comments = mongoTemplate.find(query, Comment.class);
        } catch (Exception e) {
            log.error("Error getting firstlevelcomment {} ", n);
        }
        return comments.stream()
                .map((comment) -> CommentDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .parentId(comment.getParentId())
                        .postId(comment.getPostId())
                        .localDateTime(comment.getLocalDateTime())
                        .userId(comment.getUserId())
                        .dislikesCount(comment.getDislikeCount())
                        .likesCount(comment.getLikeCount())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<CommentDto> getReply(String commentId) {
        Query query = Query.query(Criteria.where("parentId").is(commentId));
        List<Comment> comments = null;
        try {
            comments = mongoTemplate.find(query, Comment.class);
        } catch (Exception e) {
            log.error("error getting comment reply");
        }
        return comments.stream()
                .map(comment -> CommentDto.builder()
                        .commentId(comment.getCommentId())
                        .content(comment.getContent())
                        .parentId(comment.getParentId())
                        .postId(comment.getPostId())
                        .localDateTime(comment.getLocalDateTime())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public CommentDto addComment(CommentDto commentDto) {
        Comment comment = Comment.builder()
                .content(commentDto.getContent())
                .parentId(commentDto.getParentId())
                .postId(commentDto.getParentId())
                .localDateTime(LocalDateTime.now())
                .postId(commentDto.getPostId())
                .userId(commentDto.getUserId())
                .build();
        try {
            mongoTemplate.save(comment);
        } catch (Exception e) {
            log.error("error saving comment");
        }
        commentDto.setCommentId(comment.getCommentId());
        commentDto.setLocalDateTime(comment.getLocalDateTime());
        return commentDto;
    }

    @Override
    public CommentDto getComment(String commentId) {
        Comment comment = null;
        Query query = Query.query(Criteria.where("_id").is(commentId));
        try {
            comment = mongoTemplate.findOne(query, Comment.class);
        } catch (Exception e) {
            log.error("error finding post {}", commentId);
        }
        return CommentDto.builder()
                .commentId(comment.getCommentId())
                .content(comment.getContent())
                .parentId(comment.getParentId())
                .localDateTime(comment.getLocalDateTime())
                .postId(comment.getPostId())
                .likesCount(comment.getLikeCount())
                .dislikesCount(comment.getDislikeCount())
                .userId(comment.getUserId())
                .build();
    }

    public void incrementActionCount(ActionType actionType, String commentId){
        Query query = Query.query(Criteria.where("_id").is(commentId));
        String incrementKey = actionType.equals(ActionType.LIKE) ? String.valueOf("likeCount"): String.valueOf("dislikeCount");
        try{
           UpdateResult updateResult =  mongoTemplate.updateFirst(query, new Update().inc(incrementKey, 1), Comment.class);
        }catch (Exception e){
            log.error("error updating increment operation");
        }
    }
}
