package com.gavinfenton.quizolation.constant;

public interface Endpoints {

<<<<<<< HEAD
    public static String QUIZZES = "/quizzes";
    public static String QUIZ = QUIZZES + "/{quizId}";

    public static String TEAM_ID = "teamId";
    public static String TEAMS = "/teams";
    public static String TEAM = TEAMS + "/{" + TEAM_ID + "}";

=======
    String QUIZ_ID = "quizId";
    String QUIZZES = "/quizzes";
    String QUIZ = QUIZZES + "/{" + QUIZ_ID + "}";
>>>>>>> origin/master

}
