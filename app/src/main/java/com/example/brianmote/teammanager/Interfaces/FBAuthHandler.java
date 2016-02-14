package com.example.brianmote.teammanager.Interfaces;

import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Pojos.Account;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public interface FBAuthHandler {
    void register(Account account, FBCompletionListener fbCompletionListener);
    void login(Account account, FBCompletionListener fbCompletionListener);
    boolean checkLoginStatus();
    void logout(FBCompletionListener fbCompletionListener);
}
