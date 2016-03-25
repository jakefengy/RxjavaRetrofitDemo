package com.xm.demo.login.presenter.manager;

import android.text.TextUtils;

/**
 * Created by lvxia on 2016-03-25.
 */
public class LoginManager {

    public void login(String username, String psw, ILoginListener listener) {

        // TODO: login to web service

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(psw)) {
            listener.onLoginFail();
        } else {
            listener.onLoginSuccess(username + " , " + psw);
        }

    }

    public interface ILoginListener {
        void onLoginSuccess(String result);

        void onLoginFail();
    }

}
