package org.example.model;

public class Player {
    private long id;
    private String name;
    private String position;
    private String team;

    public Player() {}

    public Player(long id, String name, String position, String team) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.team = team;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }
}
