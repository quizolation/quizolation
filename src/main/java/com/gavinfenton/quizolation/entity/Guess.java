package com.gavinfenton.quizolation.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

@Data
@Entity
public class Guess implements Serializable {

    @EmbeddedId
    private GuessId guessId;

    @Column
    private String guess;

    @Column
    private int points;

}
