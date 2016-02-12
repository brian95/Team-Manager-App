package com.example.brianmote.teammanager.Interfaces;

import com.example.brianmote.teammanager.Listeners.FBAuthListener;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Models.User;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public interface FBAuthHandler {
    void register(FBCompletionListener fbCompletionListener);
    void login(FBCompletionListener fbCompletionListener);
    boolean checkLoginStatus(FBAuthListener fbAuthListener);
    User getCurrentUser();
}
