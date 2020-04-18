package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Quiz extends BaseEntity {

    @Column
    @NotBlank
    @Size(max = 32)
    private String name;

    @OneToMany(mappedBy = "quizId")
    private List<Round> rounds = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "quiz_teams",
            joinColumns = @JoinColumn(name = "quiz_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private List<Team> teams = new ArrayList<>();

    @ManyToOne
    private AppUser master;

}
