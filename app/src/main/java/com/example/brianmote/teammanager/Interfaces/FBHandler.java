package com.example.brianmote.teammanager.Interfaces;

import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Listeners.FBItemChangeListener;
import com.firebase.client.Firebase;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public interface FBHandler<T> {
    void create(T t, FBCompletionListener fbCompletionListener);
    T getById(String id);
    void populateList(Firebase ref, FBItemChangeListener fbItemChangeListener);
}
