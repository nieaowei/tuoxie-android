package com.naw.login.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */

//用户登录的记录模型，记录他的id和要显示的昵称
public class LoggedInUser {

    private String userId;
    private String displayName;

    public LoggedInUser(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}
