package com.gavinfenton.quizolation.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class GuessId implements Serializable {

    private Long teamId;

    private Long questionId;

}
