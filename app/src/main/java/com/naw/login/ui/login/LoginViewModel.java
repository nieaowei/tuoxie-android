package com.naw.login.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.Handler;
import android.util.Patterns;

import com.naw.login.data.LoginRepository;
import com.naw.login.data.Result;
import com.naw.login.data.model.LoggedInUser;
import com.naw.tuoxie_android.R;

public class LoginViewModel extends ViewModel {
    //登录界面的状态
    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    //登录的结果
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    //登录存储
    private LoginRepository loginRepository;
    //构造函数，通过登录存储库
    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }
    //获取登录状态
    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }
    //获取登录结果
    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    void setLoginResult(LoginResult value){
        loginResult.setValue(value);
    }
    //登录调用函数
    public void login(String username, String password,final  Handler loginhandler) {
        // can be launched in a separate asynchronous job
        //调用登录函数，得到结果
        Result<LoggedInUser> result = loginRepository.login(username, password,loginhandler);
//        if (result instanceof Result.Success) {//如果结果是成功类
//            //获取登录记录
//            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
//            //设置登录成功结果
//            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
//        } else {
//            //设置登录失败的结果
//            loginResult.setValue(new LoginResult(R.string.login_failed));
//        }
    }
    //登录数据的合法性检查
    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {//如果用户名不合法
            //设置登录界面状态的值
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {//如果密码不合法
            //设置登录界面状态
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            //设置状态为true
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    //用户合法性检查
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
    //密码合法性检查
    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}
