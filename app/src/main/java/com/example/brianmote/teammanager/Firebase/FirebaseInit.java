package com.example.brianmote.teammanager.Firebase;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class FirebaseInit extends Application {
    public static final String BASE_REF = "https://team-manager-mobile.firebaseio.com/";

    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
