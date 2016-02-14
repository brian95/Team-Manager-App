package com.example.brianmote.teammanager.Pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class User {
    private String displayName;
    private String id;
    @JsonIgnoreProperties("teams")
    private List<String> teams;

    public User() {

    }

    public User(String displayName) {
        this.displayName = displayName;
    }

    public User(String displayName, List<String> teams, String id) {
        this(displayName);
        this.teams = teams;
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    @JsonIgnore
    public List<String> getTeams() {
        return teams;
    }

    public String getId() {
        return id;
    }

}
