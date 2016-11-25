package com.test.data.domain;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.HashSet;
import java.util.Set;

@NodeEntity
public class Team {
    @GraphId
    private Long id;
    private String name;
    private String code;
    @Relationship(type = "WIN")
    private Set<Winning> winnings = new HashSet<>();

    public Team() {
    }

    public Winning win(int win,Playoff playoff){
        Winning winning = new Winning(win, this, playoff);
        winnings.add(winning);
        return winning;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Set<Winning> getWinnings() {
        return winnings;
    }

    public void setWinnings(Set<Winning> winnings) {
        this.winnings = winnings;
    }
}
