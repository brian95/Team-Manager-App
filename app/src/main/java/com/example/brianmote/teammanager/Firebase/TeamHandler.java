package com.example.brianmote.teammanager.Firebase;

import com.example.brianmote.teammanager.Interfaces.FBHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Models.*;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class TeamHandler implements FBHandler<Team> {
    private static final String TAG = "Team Handler";
    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);
    private Firebase currentTeamRef;
    private Firebase teamRosterRef;
    private Firebase teamsRef;
    private Team team;
    private HashMap<String, Object> map;
    private HashMap<String, Object> roster;
    private ArrayList<Team> teamsList;
    private Query query;

    private FBCompletionListener fbCompletionListener;

    public TeamHandler(Team team) {
        this.team = team;
        teamsRef = ref.child("Team");
        currentTeamRef = teamsRef.child(team.getName());
        teamRosterRef = currentTeamRef.child("roster");
    }

    public TeamHandler() {
        if (teamsRef == null) {
            teamsRef = ref.child("Team");
        }
    }

    @Override
    public void create(final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;
        if (map == null) {
            map = new HashMap<>();
        }

        map.put("name", team.getName());
        map.put("game", team.getGame());
        map.put("rank", team.getRank());

        teamsRef.push().updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                fbCompletionListener.onComplete(firebaseError);
            }
        });

//        updateOwner(fbCompletionListener);
    }

    public void updateOwner(final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;
        if (roster == null) {
            roster = new HashMap<>();
        }
        roster.put("owner: ", ref.getAuth().getProviderData().get("email"));
        teamRosterRef.updateChildren(roster, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                fbCompletionListener.onComplete(firebaseError);
            }
        });
    }

    public ArrayList<Team> getTeamsList() {
        return teamsList;
    }

    public Query getAllTeams() {
        if (teamsList == null) {
            teamsList = new ArrayList<>();
        }
        if (query == null) {
            query = teamsRef.limitToLast(20);
        }
        return query;
    }
}
