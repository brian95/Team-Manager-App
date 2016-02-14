package com.example.brianmote.teammanager.Handlers;

import android.util.Log;

import com.example.brianmote.teammanager.Firebase.FirebaseInit;
import com.example.brianmote.teammanager.Interfaces.FBHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Listeners.FBItemChangeListener;
import com.example.brianmote.teammanager.Pojos.Team;
import com.example.brianmote.teammanager.Pojos.User;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Brian Mote on 2/14/2016.
 */
public class UserHandler implements FBHandler<User> {
    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);
    private Firebase usersRef = ref.child("Users");
    private Firebase currentUserRef = usersRef.child(ref.getAuth().getUid());
    private HashMap<String, Object> map;
    private User user;
    private FBCompletionListener fbCompletionListener;
    private FBItemChangeListener fbItemChangeListener;

    public UserHandler() {

    }

    @Override
    public void create(User user, final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;
        if (map == null) {
            map = new HashMap<>();
        }

        map.put("displayName", user.getDisplayName());
        currentUserRef.updateChildren(map, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                fbCompletionListener.onComplete(firebaseError);
            }
        });
    }

    @Override
    public User getById(String id) {
        Firebase getRef = usersRef.child(id);
        getRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot shot : dataSnapshot.getChildren()) {
                    user = shot.getValue(User.class);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                user = null;
            }
        });

        return user;
    }

    @Override
    public void populateList(Firebase ref, final FBItemChangeListener fbItemChangeListener) {
        this.fbItemChangeListener = fbItemChangeListener;
        Log.d("USER HANDLER", "CALLED POPULATE LIST");

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

    public void createUserTeam(Team team) {
        Firebase userTeamsRef = currentUserRef.child("Teams");
        HashMap<String, Object> map = new HashMap<>();
        map.put(team.getName(), true);

        userTeamsRef.updateChildren(map);
    }

}
