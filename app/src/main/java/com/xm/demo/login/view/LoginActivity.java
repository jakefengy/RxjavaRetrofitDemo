package com.xm.demo.login.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xm.demo.R;
import com.xm.demo.login.presenter.ILoginPresenter;
import com.xm.demo.login.presenter.impl.LoginPresenterImpl;
import com.xm.demo.login.view.callback.ILoginCallback;

public class LoginActivity extends AppCompatActivity implements ILoginCallback {

    private final static String TAG = "LoginTag";

    private ILoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginPresenter = new LoginPresenterImpl(this);

        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "Presenter Callback start login");
                loginPresenter.login("AAA", "BBB");
            }
        });

    }

    // Presenter Callback

    @Override
    public void onLoginSuccess(String result) {
        Log.i(TAG, "Presenter Callback onLoginSuccess " + result);
    }

    @Override
    public void onLoginFail() {
        Log.i(TAG, "Presenter Callback onLoginFail");
    }
}
