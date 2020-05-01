package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Round extends BaseEntity {

    @Column(nullable = false, updatable = false)
    private Long quizId;

    @Column
    private String name;

    @Column
    private String description;

}
