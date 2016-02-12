package com.example.brianmote.teammanager.Listeners;

import com.firebase.client.FirebaseError;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public interface FBCompletionListener {
    void onComplete(FirebaseError firebaseError);
}
