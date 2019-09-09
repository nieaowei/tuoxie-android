package com.naw.login.data;

import android.util.Log;

import com.naw.login.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {

        try {
            Log.d("debug123",username+password);

            // TODO: handle loggedInUser authentication
            URL url = new URL("http://192.168.1.16:8080/login?username="+username+"&password="+password);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(500);
            connection.setReadTimeout(500);
            connection.setRequestMethod("GET");

            int temp = connection.getResponseCode();

            Log.d("debug123",String.valueOf(temp));

            if (connection.getResponseCode()!=200){
                throw new RuntimeException("error");
            }

            InputStream is = connection.getInputStream();
            //读取信息BufferReader
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuffer buffer = new StringBuffer();
            String readLine = "";
            while ((readLine = reader.readLine()) != null) {
                buffer.append(readLine);
            }
            Log.d("debug123",buffer.toString());
            is.close();
            reader.close();
            connection.disconnect();

            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);

        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public void loginThread(){

    }
}
