package com.gavinfenton.quizolation.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoundDTO extends BaseEntityDTO {

    private String name;

    private String description;

}
