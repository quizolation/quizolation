package com.gavinfenton.webquiz.entity;

import lombok.Data;

import java.util.List;

@Data
public class Quiz extends BaseEntity {

    private String name;

    private List<Round> rounds;

    private List<Team> teams;

}
