package com.example.brianmote.teammanager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.brianmote.teammanager.Adapters.PageAdapter;
import com.example.brianmote.teammanager.Fragments.FindTeamsFrag;
import com.example.brianmote.teammanager.Fragments.MessagesFragment;
import com.example.brianmote.teammanager.Fragments.YourTeamsFrag;
import com.example.brianmote.teammanager.Handlers.AuthHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.R;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeScreenActivity extends AppCompatActivity
        implements YourTeamsFrag.OnFragmentInteractionListener, FindTeamsFrag
        .OnFragmentInteractionListener, MessagesFragment.OnFragmentInteractionListener {

    private static final String TAG = "Homescreen Activity";
    private PageAdapter pageAdapter;
    private AuthHandler authHandler;
    private ProgressDialog dialog;

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
        pager.setOffscreenPageLimit(10);

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

            case R.id.action_logout:
                if (dialog == null) {
                    dialog = new ProgressDialog(HomeScreenActivity.this);
                }
                dialog.setMessage("Loading...");
                dialog.setCancelable(false);

                if (!dialog.isShowing()) {
                    dialog.show();
                }

                if (authHandler == null) {
                    authHandler = new AuthHandler();
                }

                authHandler.logout(new FBCompletionListener() {
                    @Override
                    public void onComplete(FirebaseError firebaseError) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        String msg;
                        if (firebaseError == null) {
                            msg = "Logging out";
                            startActivity(new Intent(HomeScreenActivity.this, LoginActivity.class));
                        } else {
                            msg = firebaseError.getMessage();
                        }
                        Toast.makeText(HomeScreenActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                });
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
