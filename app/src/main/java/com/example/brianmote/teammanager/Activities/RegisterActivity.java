package com.example.brianmote.teammanager.Activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brianmote.teammanager.Firebase.UserHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Models.User;
import com.example.brianmote.teammanager.R;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private User user;
    private UserHandler handler;
    private ProgressDialog dialog;

    @Bind(R.id.registerEmail)
    EditText registerEmail;
    @Bind(R.id.registerPassword)
    EditText registerPassword;
    @Bind(R.id.submitBtn)
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        if (dialog == null) {
            dialog = new ProgressDialog(RegisterActivity.this);
        }

        if (!dialog.isShowing()) {
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        String email = registerEmail.getText().toString();
        String password = registerPassword.getText().toString();
        if (user == null) {
            user = new User(email);
        }
        user.setPassword(password);

        if (handler == null) {
            handler = new UserHandler(user);
        }

        handler.register(new FBCompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                String msg;
                if (firebaseError == null) {
                    msg = "Account Created!";
                } else {
                    msg = firebaseError.getMessage();
                    Log.d(TAG, msg);
                }
                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

}
