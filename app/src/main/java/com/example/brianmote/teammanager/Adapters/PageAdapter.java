package com.example.brianmote.teammanager.Adapters;

import android.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.brianmote.teammanager.Fragments.FindTeamFragment;
import com.example.brianmote.teammanager.Fragments.MessagesFragment;
import com.example.brianmote.teammanager.Fragments.YourTeamsFragment;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class PageAdapter extends FragmentStatePagerAdapter {
    private int numOfTabs;

    public PageAdapter(android.support.v4.app.FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                YourTeamsFragment yourTeams = YourTeamsFragment.newInstance(position + 1);
                return yourTeams;

            case 1:
                FindTeamFragment findTeam = FindTeamFragment.newInstance(position + 2);
                return findTeam;

            case 2:
                MessagesFragment messages = MessagesFragment.newInstance(position + 3);
                return messages;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
