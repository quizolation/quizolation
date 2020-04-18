package com.gavinfenton.quizolation.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
public class Team extends BaseEntity {

    @Column
    private String name;

    @ManyToMany
    @JoinTable(
            name = "app_users_teams",
            joinColumns = @JoinColumn(name = "app_user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "team_id", referencedColumnName = "id"))
    private List<Team> teams = new ArrayList<>();

}
