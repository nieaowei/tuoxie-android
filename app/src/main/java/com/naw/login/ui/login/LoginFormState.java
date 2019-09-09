package com.naw.login.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */

//登录时各个控件的状态处理
class LoginFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer passwordError;
    private boolean isDataValid;

    //登录窗口状态处理，根据用户和密码错误
    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }
    //根据数据的合法性处理
    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    //得到用户错误的编号
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    //得到密码错误的编号
    Integer getPasswordError() {
        return passwordError;
    }

    //检查错误的合法性
    boolean isDataValid() {
        return isDataValid;
    }
}
