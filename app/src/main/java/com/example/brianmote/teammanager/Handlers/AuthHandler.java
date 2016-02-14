package com.example.brianmote.teammanager.Handlers;

import com.example.brianmote.teammanager.Firebase.FirebaseInit;
import com.example.brianmote.teammanager.Interfaces.FBAuthHandler;
import com.example.brianmote.teammanager.Listeners.FBAuthListener;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Listeners.FBItemChangeListener;
import com.example.brianmote.teammanager.Pojos.Account;
import com.example.brianmote.teammanager.Pojos.Team;
import com.example.brianmote.teammanager.Pojos.User;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.HashMap;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class AuthHandler implements FBAuthHandler{
    private static final String TAG = "AuthHandler";

//    private final Account account;

    private Firebase ref = new Firebase(FirebaseInit.BASE_REF);
    private Firebase usersRef = ref.child("Users");

    private FBCompletionListener fbCompletionListener;
    private FBAuthListener fbAuthListener;
    private FBItemChangeListener fbItemChangeListener;
    private UserHandler userHandler;

    private boolean isLogged;

    public AuthHandler() {

    }

    public static Account getCurrentAccount() {
        Firebase currentRef = new Firebase(FirebaseInit.BASE_REF);
        String email = currentRef.getAuth().getProviderData().get("email").toString();
        Account currentAcct = new Account(email);
        return currentAcct;
    }

    /**
     * Creates a new account with the Users Email and Password
     * This does not login the account to the app, it only creates the account
     * @param fbCompletionListener - Callback methods
     */
    @Override
    public void register(Account account, final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;

        ref.createUser(account.getEmail(), account.getPassword(), new Firebase.ResultHandler() {
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

    /**
     * Logs in the account with their Email and Password
     * Creates a User object in our Firebase
     * @param fbCompletionListener - Callback methods
     */
    @Override
    public void login(final Account account, final FBCompletionListener fbCompletionListener) {
        this.fbCompletionListener = fbCompletionListener;

        ref.authWithPassword(account.getEmail(), account.getPassword(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                //Creating User Object
                if (userHandler == null) {
                    userHandler = new UserHandler();
                }

                User user = new User(account.getEmail());
                userHandler.create(user, fbCompletionListener);
                fbCompletionListener.onComplete(null);
            }

            @Override
            public void onAuthenticationError(FirebaseError firebaseError) {
                fbCompletionListener.onComplete(firebaseError);
            }
        });
    }


    @Override
    public boolean checkLoginStatus() {
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null) {
                    isLogged = true;
                } else {
                    isLogged = false;
                }
            }
        });
        return isLogged;
    }

    @Override
    public void logout(FBCompletionListener fbCompletionListener) {
        ref.unauth();
        fbCompletionListener.onComplete(null);
    }

    public void createTeam(Team team) {
        Firebase currentUserRef = usersRef.child(ref.getAuth().getUid());
        Firebase userTeamRef = currentUserRef.child("Teams");
        HashMap<String, Object> userTeamMap = new HashMap<>();
        userTeamMap.put(team.getName(), true);

        userTeamRef.updateChildren(userTeamMap);
    }
}
