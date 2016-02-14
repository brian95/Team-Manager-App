package com.example.brianmote.teammanager.Handlers;

import com.example.brianmote.teammanager.Firebase.FirebaseInit;
import com.example.brianmote.teammanager.Interfaces.FBHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Listeners.FBItemChangeListener;
import com.example.brianmote.teammanager.Pojos.Team;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class TeamHandler implements FBHandler<Team> {
    private static final String TAG = "Team Handler";
    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);
    private Firebase teamsRef = ref.child("Teams");
    private Team team;
    private HashMap<String, Object> map;
    private HashMap<String, Object> roster;
    private ArrayList<Team> teamsList;
    private Query query;

    private FBCompletionListener fbCompletionListener;
    private FBItemChangeListener fbItemChangeListener;

    public TeamHandler() {

    }

    @Override
    public void create(Team team, final FBCompletionListener fbCompletionListener) {
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
    }

    public Query getAllTeams() {
        if (query == null) {
            query = teamsRef.limitToLast(20);
        }
        return query;
    }

    @Override
    public Team getById(String id) {
        Firebase teamIdRef = teamsRef.child(id);
        teamIdRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    team = shot.getValue(Team.class);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                team = null;
            }
        });
        return team;
    }

    @Override
    public void populateList(Firebase ref, final FBItemChangeListener fbItemChangeListener) {
        this.fbItemChangeListener = fbItemChangeListener;
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fbItemChangeListener.onItemChanged(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fbItemChangeListener.onItemChanged(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                fbItemChangeListener.onItemDeleted(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                fbItemChangeListener.onItemChanged(dataSnapshot);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                fbItemChangeListener.onCancel(firebaseError);
            }
        });
    }

}
