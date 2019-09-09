package com.naw.login.data;

import com.naw.login.data.model.LoggedInUser;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private LoggedInUser user = null;

    // private constructor : singleton access
    private LoginRepository(LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
        }
        return instance;
    }
    //检查是否已经记录下登录信息
    public boolean isLoggedIn() {
        return user != null;
    }
    //登出
    public void logout() {
        user = null;
        dataSource.logout();
    }
    //设置登录记录
    private void setLoggedInUser(LoggedInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
    //使用用户名登录，并返回登录结果
    public Result<LoggedInUser> login(String username, String password) {
        // handle login
        //调用数据资源里面的登录去检查登录信息，得到结果
        Result<LoggedInUser> result = dataSource.login(username, password);
        //根据结果判断是否成功
        if (result instanceof Result.Success) {//如果成功设置数据
            setLoggedInUser(((Result.Success<LoggedInUser>) result).getData());
        }
        //如果失败返回null
        return result;
    }
}
