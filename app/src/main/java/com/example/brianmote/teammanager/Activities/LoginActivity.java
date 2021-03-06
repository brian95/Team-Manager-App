package com.example.brianmote.teammanager.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.brianmote.teammanager.Handlers.AuthHandler;
import com.example.brianmote.teammanager.Listeners.FBCompletionListener;
import com.example.brianmote.teammanager.Pojos.Account;
import com.example.brianmote.teammanager.R;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private Account account;
    private AuthHandler handler;
    private ProgressDialog dialog;
    private Firebase ref;

    @Bind(R.id.loginEmail)
    EditText loginEmail;
    @Bind(R.id.loginPassword)
    EditText loginPassword;
    @Bind(R.id.loginBtn)
    Button loginBtn;
    @Bind(R.id.registerBtn)
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");
        ButterKnife.bind(this);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        checkLoginStatus();
    }

    private void login() {
        if (dialog == null) {
            dialog = new ProgressDialog(LoginActivity.this);
        }

        if (!dialog.isShowing()) {
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        String email = loginEmail.getText().toString();
        String password = loginPassword.getText().toString();
        if (account == null) {
            account = new Account(email);
        }
        account.setPassword(password);

        if (handler == null) {
            handler = new AuthHandler();
        }

        handler.login(account, new FBCompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                String msg;
                if (firebaseError == null) {
                    msg = "Logging in";
                    Intent intent = new Intent(LoginActivity.this, HomeScreenActivity.class);
                    startActivity(intent);
                } else {
                    msg = firebaseError.getMessage();
                }
                Log.d(TAG, msg);
                Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkLoginStatus() {
        if (handler == null) {
            handler = new AuthHandler();
        }

        if (handler.checkLoginStatus()) {
            startActivity(new Intent(LoginActivity.this, HomeScreenActivity.class));
        }
    }

}
