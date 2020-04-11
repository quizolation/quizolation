package com.gavinfenton.quizolation.entity;

import lombok.Data;

import java.util.List;

@Data
public class Round extends BaseEntity {

    private String name;

    private String description;

    private List<Question> questions;

}
