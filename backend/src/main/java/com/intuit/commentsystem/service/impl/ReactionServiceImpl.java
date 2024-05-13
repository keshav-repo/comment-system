package com.intuit.commentsystem.service.impl;

import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Reaction;
import com.intuit.commentsystem.document.ReactionType;
import com.intuit.commentsystem.document.User;
import com.intuit.commentsystem.dto.ReactionParticipent;
import com.intuit.commentsystem.service.CommentService;
import com.intuit.commentsystem.service.ReactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ReactionServiceImpl implements ReactionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CommentService commentService;

    @Override
    public List<ReactionParticipent> getReactionParticipant(ActionType actionType, ReactionType reactionType, String refId) {
        Query query = Query.query(
                Criteria.where("refId").is(refId)
                        .and("actionType").is(actionType.toString())
        );
        List<Reaction> reactionList = null;
        try {
            reactionList = mongoTemplate.find(query, Reaction.class);
        } catch (Exception e) {
            log.info("error getting participent of like or dislikes");
        }

        List<String> userIdList = reactionList.stream().map(r -> r.getUserId()).distinct().collect(Collectors.toList());
        Query query2 = Query.query(Criteria.where("_id").in(userIdList));

        List<User> users =  mongoTemplate.find(query2, User.class);
        Map<String, User> userMapping = users.stream().collect(Collectors.toMap(User::getUserId, Function.identity()));

        return reactionList.stream()
                .map(reaction -> ReactionParticipent.builder()
                        .userName(userMapping.get(reaction.getUserId()).getUserName()).build())
                .collect(Collectors.toList());
    }

    @Override
    public void react(Reaction reaction) {
        try {
            mongoTemplate.save(reaction);
            commentService.incrementActionCount(reaction.getActionType(), reaction.getRefId());
        } catch (Exception exception) {
            log.error("error saving reaction");
        }
    }
}
