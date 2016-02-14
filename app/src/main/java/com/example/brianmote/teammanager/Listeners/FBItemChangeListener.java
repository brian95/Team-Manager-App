package com.example.brianmote.teammanager.Listeners;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;

/**
 * Created by Brian Mote on 2/12/2016.
 */
public interface FBItemChangeListener {
    void onItemChanged(DataSnapshot dataSnapshot);
    void onItemDeleted(DataSnapshot dataSnapshot);
    void onCancel(FirebaseError error);
}
