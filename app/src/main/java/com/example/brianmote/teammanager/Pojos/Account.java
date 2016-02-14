package com.example.brianmote.teammanager.Pojos;

/**
 * Created by Brian Mote on 2/14/2016.
 */
public class Account {
    private String email;
    private String password;

    public Account(String email) {
        this.email = email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
