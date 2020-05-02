package com.gavinfenton.quizolation.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class QuestionDTO extends BaseEntityDTO {

    private String question;

    private int points;

}
