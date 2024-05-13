package com.intuit.commentsystem.document;

public enum ReactionType {
    POST, COMMENT;
    public static ReactionType of(String type) {
        switch (type) {
            case "POST":
                return ReactionType.POST;
            case "COMMENT":
                return ReactionType.COMMENT;
        }
        return null;
    }
}
