package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Question extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private Long roundId;

    @Column
    private String question;

    @Column
    private String answer;

    @Column
    private int points;

}
