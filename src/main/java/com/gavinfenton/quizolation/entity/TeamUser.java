package com.gavinfenton.quizolation.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

@Data
@Entity
@IdClass(TeamUserId.class)
public class TeamUser {

    @Id
    private Long teamId;

    @Id
    private Long userId;

}
