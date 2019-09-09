package com.naw.login.ui.login;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class LoginFormState {
    @Nullable
    private Integer usernameError;//用户状态代码
    @Nullable
    private Integer passwordError;//密码错误代码
    private boolean isDataValid;//数据合法性表示
    //根据用户和密码的错误去构造该类
    LoginFormState(@Nullable Integer usernameError, @Nullable Integer passwordError) {
        this.usernameError = usernameError;
        this.passwordError = passwordError;
        this.isDataValid = false;
    }
    //根据数据的合法性，构造该类
    LoginFormState(boolean isDataValid) {
        this.usernameError = null;
        this.passwordError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    //返回错误代码
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }
    //返回数据状态
    boolean isDataValid() {
        return isDataValid;
    }
}
