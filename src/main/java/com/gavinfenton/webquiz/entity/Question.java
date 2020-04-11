package com.gavinfenton.webquiz.entity;

import lombok.Data;

@Data
public class Question extends BaseEntity {

    private String question;

    private String answer;

    private int points;

}
