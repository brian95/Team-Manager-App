package com.example.brianmote.teammanager.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.brianmote.teammanager.Adapters.PageAdapter;
import com.example.brianmote.teammanager.Fragments.FindTeamFragment;
import com.example.brianmote.teammanager.Fragments.MessagesFragment;
import com.example.brianmote.teammanager.Fragments.YourTeamsFragment;
import com.example.brianmote.teammanager.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity
        implements FindTeamFragment.OnFragmentInteractionListener, YourTeamsFragment
        .OnFragmentInteractionListener, MessagesFragment.OnFragmentInteractionListener {

    private static final String TAG = "Homescreen Activity";
    private PageAdapter pageAdapter;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;
    @Bind(R.id.pager)
    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Team Manager");
        ButterKnife.bind(this);

        setupFrags();
    }

    private void setupFrags() {
        tabLayout.addTab(tabLayout.newTab().setText("Your Teams"));
        tabLayout.addTab(tabLayout.newTab().setText("Find A Team"));
        tabLayout.addTab(tabLayout.newTab().setText("Messages"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        if (pageAdapter == null) {
            pageAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        }

        pager.setAdapter(pageAdapter);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_homescreen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_team:
                startActivity(new Intent(HomeScreenActivity.this, CreateTeamActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
