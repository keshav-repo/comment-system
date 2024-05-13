package com.intuit.commentsystem.document;

public enum ActionType {
    LIKE, DISLIKE;
    public static ActionType of(String type) {
        switch (type) {
            case "LIKE":
                return ActionType.LIKE;
            case "DISLIKE":
                return ActionType.DISLIKE;
        }
        return null;
    }

    public static void main(String[] args) {
        ActionType actionType = ActionType.DISLIKE;

        System.out.println(actionType.equals(ActionType.DISLIKE) );
    }
}
