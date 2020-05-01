package com.gavinfenton.quizolation.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(QuizTeamId.class)
public class QuizTeam {

    @Id
    private Long quizId;

    @Id
    private Long teamId;

}
