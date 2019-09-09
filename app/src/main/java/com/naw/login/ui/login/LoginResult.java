package com.naw.login.ui.login;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private Integer error;
    //通过错误去构造结果
    LoginResult(@Nullable Integer error) {
        this.error = error;
    }
    //通过 成功登录的用户记录去构造
    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }
    //返回成功的信息 如果为null即失败
    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }
    //返回错误代码
    @Nullable
    Integer getError() {
        return error;
    }
}
