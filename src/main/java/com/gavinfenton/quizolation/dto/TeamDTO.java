package com.gavinfenton.quizolation.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class TeamDTO extends BaseEntityDTO {

    private String name;
    
}
