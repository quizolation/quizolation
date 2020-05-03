package com.gavinfenton.quizolation.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDetailsDTO extends BaseEntityDTO {

    private String name;

    private String username;

    private String email;

}