package com.supersmashcoders.backtobackhackathon.global;

import com.supersmashcoders.backtobackhackathon.models.UserModel;

public class UserHandler {
    static UserModel user = null;
    public static String getUsername() {
        return UserHandler.user.getUsername();
    }
    public static void setUser (UserModel user) {
        UserHandler.user = user;
    }
}
