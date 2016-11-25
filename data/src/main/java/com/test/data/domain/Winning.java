package com.test.data.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type = "WIN")
public class Winning {
    @GraphId
    private Long id;
    private int win;
    @StartNode
    @JsonBackReference
    private Team team;
    @EndNode
    @JsonBackReference
    private Playoff playoff;

    public Winning() {
    }

    public Winning(int win, Team team, Playoff playoff) {
        this.win = win;
        this.team = team;
        this.playoff = playoff;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getWin() {
        return win;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Playoff getPlayoff() {
        return playoff;
    }

    public void setPlayoff(Playoff playoff) {
        this.playoff = playoff;
    }
}
