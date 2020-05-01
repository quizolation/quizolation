package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Quiz extends BaseEntity {

    @Column
    private Long masterId;

    @Column
    @NotBlank(message = "Quiz name must be given")
    @Size(max = 32)
    private String name;

}
