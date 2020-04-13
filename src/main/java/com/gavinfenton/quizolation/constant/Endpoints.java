package com.gavinfenton.quizolation.constant;

public interface Endpoints {

    String QUIZ_ID = "quizId";
    String QUIZZES = "/quizzes";
    String QUIZ = QUIZZES + "/{" + QUIZ_ID + "}";

    String TEAM_ID = "teamId";
    String TEAMS = "/teams";
    String TEAM = TEAMS + "/{" + TEAM_ID + "}";

}
