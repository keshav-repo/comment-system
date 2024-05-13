package com.intuit.commentsystem.service;

import com.intuit.commentsystem.document.Reaction;
import com.intuit.commentsystem.document.ReactionType;
import java.util.List;

public interface ReactionService {
    /**
     * get all the reaction for a particular type
     * @param commentId
     * @param reactionType
     * @return
     */
    public List<Reaction> getReaction(String commentId, ReactionType reactionType);


    /**
     * add a reaction to a comment. it can be either like or dislike
     * @param commentId
     * @param reactionType
     */
    public void react(String commentId, ReactionType reactionType);
}
