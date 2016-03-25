package com.xm.demo.login.presenter.impl;

import com.xm.demo.login.presenter.ILoginPresenter;
import com.xm.demo.login.presenter.manager.LoginManager;
import com.xm.demo.login.view.callback.ILoginCallback;

/**
 *
 */
public class LoginPresenterImpl implements ILoginPresenter {

    private ILoginCallback loginView;
    private LoginManager loginManager;

    public LoginPresenterImpl(ILoginCallback loginView) {
        this.loginView = loginView;
        loginManager = new LoginManager();
    }

    @Override
    public void login(String username, String psw) {
        loginManager.login(username, psw, new LoginManager.ILoginListener() {
            @Override
            public void onLoginSuccess(String result) {
                loginView.onLoginSuccess(result);
            }

            @Override
            public void onLoginFail() {
                loginView.onLoginFail();
            }
        });
    }

}
