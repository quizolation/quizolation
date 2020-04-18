package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class AppUser extends BaseEntity {

    private String name;

    private String username;

    private String email;
    
    private String password;

}
