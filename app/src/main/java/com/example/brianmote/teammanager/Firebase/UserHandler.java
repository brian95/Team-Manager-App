package com.example.brianmote.teammanager.Firebase;

import com.example.brianmote.teammanager.Interfaces.FBAuthHandler;
import com.example.brianmote.teammanager.Listeners.FBAuthListener;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Models.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class UserHandler implements FBAuthHandler{
    private static final java.lang.String TAG = "UserHandler";

    private final User user;

    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);
    private Firebase usersRef = ref.child("Users");
    private FBCompletionListener fbCompletionListener;
    private FBAuthListener fbAuthListener;
    private boolean isLogged;

    public UserHandler(User user) {
        this.user = user;
    }

    @Override
    public void register(final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;

        ref.createUser(user.getEmail(), user.getPassword(), new Firebase.ResultHandler() {
            @Override
            public void onSuccess() {
                fbCompletionListener.onComplete(null);
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                fbCompletionListener.onComplete(firebaseError);
            }
        });
    }

    @Override
    public void login(final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;

        ref.authWithPassword(user.getEmail(), user.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                HashMap<java.lang.String, Object> userMap = new HashMap<java.lang.String, Object>();
                userMap.put("email", user.getEmail());
                usersRef.child(authData.getUid()).updateChildren(userMap);
                fbCompletionListener.onComplete(null);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                fbCompletionListener.onComplete(firebaseError);
            }
        });
    }

    @Override
    public boolean checkLoginStatus(final FBAuthListener fbAuthListener) {
        this.fbAuthListener = fbAuthListener;
        final java.lang.String error;
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData == null) {
                    isLogged = true;
                } else {
                    isLogged = false;
                }
            }
        });
        return isLogged;
    }

    @Override
    public User getCurrentUser() {
        return this.user;
    }
}
