package com.example.brianmote.teammanager.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.brianmote.teammanager.Handlers.AuthHandler;
import com.example.brianmote.teammanager.Handlers.TeamHandler;
import com.example.brianmote.teammanager.Handlers.UserHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Pojos.Account;
import com.example.brianmote.teammanager.Pojos.Team;
import com.example.brianmote.teammanager.Pojos.User;
import com.example.brianmote.teammanager.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CreateTeamActivity extends AppCompatActivity {
    private static final String TAG = "Create Team Activity";
    private Team team;
    private User owner;
    private TeamHandler teamHandler;
    private UserHandler userHandler;
    private ArrayAdapter<CharSequence> gameAdapter;
    private ArrayAdapter<CharSequence> csgoRankAdapter;
    private ArrayAdapter<CharSequence> lolRankAdapter;
    private ProgressDialog dialog;
    private Firebase ref;

    @Bind(R.id.createTeamName)
    EditText createTeamName;
    @Bind(R.id.createTeamGame)
    Spinner createTeamGame;
    @Bind(R.id.createTeamRank)
    Spinner createTeamRank;
    @Bind(R.id.createTeamDescription)
    EditText createTeamDesc;
    @Bind(R.id.createBtn)
    Button createBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_team);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Create Team");
        ButterKnife.bind(this);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createTeam();
            }
        });

        setupAdapters();
    }

    private void createTeam() {
        String name = createTeamName.getText().toString();
        String game = createTeamGame.getSelectedItem().toString();
        String rank = createTeamRank.getSelectedItem().toString();
        if (dialog == null) {
            dialog = new ProgressDialog(CreateTeamActivity.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
        }
        if (!dialog.isShowing()) {
            dialog.show();
        }

        if (team == null) {
            team = new Team(name, game, rank);
        }

        if (teamHandler == null) {
            teamHandler = new TeamHandler();
        }

        if (userHandler == null) {
            Account currentAccount = AuthHandler.getCurrentAccount();
            userHandler = new UserHandler();
        }

        teamHandler.create(team, new FBCompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                String msg;
                if (firebaseError == null) {
                    msg = "Team Created";
                    userHandler.createUserTeam(team);
                } else {
                    msg = firebaseError.getMessage();
                }
                Toast.makeText(CreateTeamActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupAdapters() {
        if (gameAdapter == null) {
            gameAdapter = ArrayAdapter.createFromResource(CreateTeamActivity.this, R.array
                    .game_names, R.layout.support_simple_spinner_dropdown_item);
        }

        if (lolRankAdapter == null) {
            lolRankAdapter = ArrayAdapter.createFromResource(CreateTeamActivity.this, R
                    .array.league_ranks, R.layout.support_simple_spinner_dropdown_item);
        }

        if (csgoRankAdapter == null) {
            csgoRankAdapter = ArrayAdapter.createFromResource(CreateTeamActivity.this, R
                    .array.csgo_ranks, R.layout.support_simple_spinner_dropdown_item);
        }

        createTeamGame.setAdapter(gameAdapter);

        switch (createTeamGame.getSelectedItemPosition()) {
            case 0:
                createTeamRank.setAdapter(lolRankAdapter);
                break;
            case 1:
                createTeamRank.setAdapter(csgoRankAdapter);
                break;
        }
    }

}
