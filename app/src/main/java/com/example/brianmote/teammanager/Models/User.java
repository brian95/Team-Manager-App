package com.example.brianmote.teammanager.Models;

/**
 * Created by Brian Mote on 2/11/2016.
 */
public class User {
    private String email;
    private String password;

    public User() {

    }

    public User(String email) {
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
