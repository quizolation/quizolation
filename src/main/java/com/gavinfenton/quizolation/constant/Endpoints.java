package com.gavinfenton.quizolation.constant;

public interface Endpoints {

    // ID Path Variables
    String QUIZ_ID = "quizId";
    String ROUND_ID = "roundId";
    String QUESTION_ID = "questionId";
    String TEAM_ID = "teamId";
    String USER_ID = "userId";

    String QUIZZES = "/quizzes";
    String QUIZ = QUIZZES + "/{" + QUIZ_ID + "}";

    String ROUNDS = "/rounds";
    String ROUND = ROUNDS + "/{" + ROUND_ID + "}";

    String QUESTIONS = "/questions";
    String QUESTION = QUESTIONS + "/{" + QUESTION_ID + "}";

    String TEAMS = "/teams";
    String TEAM = TEAMS + "/{" + TEAM_ID + "}";

    String USERS = "/users";
    String USER = USERS + "/{" + USER_ID + "}";

}
