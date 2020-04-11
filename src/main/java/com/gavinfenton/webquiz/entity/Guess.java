package com.gavinfenton.webquiz.entity;

import lombok.Data;

@Data
public class Guess {

    private Team team;

    private Question question;

    private String guess;

    private int points;

}
