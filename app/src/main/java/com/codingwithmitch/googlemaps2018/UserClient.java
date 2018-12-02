package com.codingwithmitch.googlemaps2018;

import android.app.Application;

import com.codingwithmitch.googlemaps2018.models.User;


public class UserClient extends Application {

    //Extends applications, means we can have only a single user logged in
    //and authenticated at any given time
    //Users are unique and do not change very often

    private User user = null;

    public User getUser() {
        return user;
    }

    //Set as soon as user is authenticated
    public void setUser(User user) {
        this.user = user;
    }

}
