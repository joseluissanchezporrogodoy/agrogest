package com.example.joseluissanchez_porrogodoy.agrogest.ui.models;

import com.google.firebase.database.IgnoreExtraProperties;


@IgnoreExtraProperties
public class User {

    public String password;
    public String email;

    public User() {

    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }

}

