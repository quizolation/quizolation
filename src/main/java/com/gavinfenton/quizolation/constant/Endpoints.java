package com.gavinfenton.quizolation.constant;

public interface Endpoints {

    String QUIZ_ID = "quizId";
    String QUIZZES = "/quizzes";
    String QUIZ = QUIZZES + "/{" + QUIZ_ID + "}";

    String ROUND_ID = "roundId";
    String ROUNDS = QUIZ + "/rounds";
    String ROUND = ROUNDS + "/{" + ROUND_ID + "}";

    String QUESTION_ID = "questionId";
    String QUESTIONS = ROUND + "/questions";
    String QUESTION = QUESTIONS + "/{" + QUESTION_ID + "}";

    String TEAM_ID = "teamId";
    String TEAMS = "/teams";
    String TEAM = TEAMS + "/{" + TEAM_ID + "}";

}
