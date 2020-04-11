package com.gavinfenton.quizolation.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Guess implements Serializable {

    @Id
    private Long teamId;

    @Id
    private Long questionId;

    @Column
    private String guess;

    @Column
    private int points;

}
