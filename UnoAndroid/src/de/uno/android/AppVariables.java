package de.uno.android;

import android.app.Application;
import de.uno.android.usermanagement.User;

public class AppVariables extends Application {

    private String username = "";

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
