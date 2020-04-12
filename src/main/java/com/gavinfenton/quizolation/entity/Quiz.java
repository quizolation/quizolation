package com.gavinfenton.quizolation.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Round> rounds = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "quiz_teams",
            joinColumns = @JoinColumn(name = "quiz_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private List<Team> teams = new ArrayList<>();

}
