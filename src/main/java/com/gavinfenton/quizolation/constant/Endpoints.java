package com.gavinfenton.quizolation.constant;

public interface Endpoints {

    // ID Path Variables
    String QUIZ_ID = "quizId";
    String ROUND_ID = "roundId";
    String QUESTION_ID = "questionId";
    String TEAM_ID = "teamId";

    // /quizzes
    // /quizzes/{quizId}
    String QUIZZES = "/quizzes";
    String QUIZ = QUIZZES + "/{" + QUIZ_ID + "}";

    // /rounds
    // /quizzes/{quizId}/rounds
    String ROUNDS = "/rounds";
    String QUIZ_ROUNDS = QUIZ + ROUNDS;

    // /rounds/{roundId}
    String ROUND = ROUNDS + "/{" + ROUND_ID + "}";

    // /questions
    // /rounds/{roundId}/questions
    String QUESTIONS = "/questions";
    String ROUND_QUESTIONS = ROUND + QUESTIONS;

    // /questions/{questionId}
    String QUESTION = QUESTIONS + "/{" + QUESTION_ID + "}";

    String TEAMS = "/teams";
    String TEAM = TEAMS + "/{" + TEAM_ID + "}";

}
