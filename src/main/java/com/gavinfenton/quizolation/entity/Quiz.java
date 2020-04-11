package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Quiz extends BaseEntity {

    @Column
    private String name;

    @OneToMany(mappedBy = "quizId")
    private List<Round> rounds;

    @ManyToMany
    @JoinTable(
            name = "quizTeams",
            joinColumns = @JoinColumn(name = "quiz_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private List<Team> teams;

}
