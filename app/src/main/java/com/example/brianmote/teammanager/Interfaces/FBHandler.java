package com.example.brianmote.teammanager.Interfaces;

import com.example.brianmote.teammanager.Listeners.FBCompletionListener;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public interface FBHandler<T> {
    void create(FBCompletionListener fbCompletionListener);
}
