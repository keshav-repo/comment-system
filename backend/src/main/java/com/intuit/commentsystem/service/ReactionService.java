package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.ActionType;
import com.intuit.commentsystem.document.Reaction;
import com.intuit.commentsystem.document.ReactionType;
import com.intuit.commentsystem.dto.ReactionParticipent;

import java.util.List;

public interface ReactionService {
    /**
     * get all the reaction for a particular type for the reference id of post, comment or anything else
     * @param actionType, reactionType, refId
     * @param reactionType
     * @param refId
     * @return
     */
    public List<ReactionParticipent> getReactionParticipant(ActionType actionType, ReactionType reactionType, String refId);


    /**
     * @param reaction
     */
    public void react(Reaction reaction);
}
