package de.uno.android;

import android.app.Application;
import de.uno.android.usermanagement.User;

public class AppVariables extends Application {

    private User user = new User("test", "test123");

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
