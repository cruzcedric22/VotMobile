package com.example.voting;


import android.app.Application;

public class GlobalClass extends Application {
    private String user_id;
    private String username;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {return username;}

    public void setUsername(String username) {
        this.username = username;
    }


}
