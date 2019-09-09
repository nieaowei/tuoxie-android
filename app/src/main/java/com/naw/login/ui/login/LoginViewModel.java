package com.naw.login.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.util.Patterns;

import com.naw.login.data.LoginRepository;
import com.naw.login.data.Result;
import com.naw.login.data.model.LoggedInUser;
import com.naw.tuoxie_android.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }
    //按键监听的方法，登录
    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        //返回登录结果
        Result<LoggedInUser> result = loginRepository.login(username, password);
        //根据结果去执行操作
        if (result instanceof Result.Success) {//成功
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {//失败
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }
    //编辑框改变事件监听
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {//不合法的用户名
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {//不合法的密码
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {//成功
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    //检查用户名的合法性
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    //检查密码的合法性
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
