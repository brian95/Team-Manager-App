package com.example.brianmote.teammanager.Pojos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.firebase.client.annotations.NotNull;

import java.util.List;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class Team{
    private String name;
    private String game;
    private String rank;
//    private int numMembers;

    @JsonProperty("roster")
    private List<String> roster;

    public Team() {
        //Required for Firebase
    }

    public Team(@NotNull String name, @NotNull String game, @NotNull String rank) {
        this.name = name;
        this.game = game;
        this.rank = rank;
    }

    public String getName() {
        return name;
    }

    public String getGame() {
        return game;
    }

    public String getRank() {
        return rank;
    }

//    public int getNumMembers() {
//        return numMembers;
//    }
//
//    @JsonIgnore
//    public void setNumMembers(int number) {
//        this.numMembers = number;
//    }

    @JsonIgnore
    public List<String> getRoster() {
        return roster;
    }
}
