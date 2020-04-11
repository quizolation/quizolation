package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Round extends BaseEntity {

    @Column
    private Long quizId;

    @Column
    private String name;

    @Column
    private String description;

    @OneToMany(mappedBy = "roundId")
    private List<Question> questions;

}
