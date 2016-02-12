package com.example.brianmote.teammanager.Models;

import android.support.annotation.Nullable;

import com.example.brianmote.teammanager.Interfaces.FBTeam;
import com.firebase.client.annotations.NotNull;

import java.util.List;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class Team{
    private String name;
    private String game;
    private String rank;

    private Team() {
        //Required for Firebase
    }

    public Team(@NotNull String name, @NotNull String game, @NotNull String rank) {
        this.name = name;
        this.game = game;
        this.rank = rank;
    }

//    @Override
    public String getName() {
        return name;
    }

//    @Override
    public String getGame() {
        return game;
    }

//    @Override
    public String getRank() {
        return rank;
    }

}
