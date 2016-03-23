package com.xm.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xm.demo.retrofit.CancelSubscriber;
import com.xm.demo.retrofit.RetrofitUtils;
import com.xm.demo.retrofit.entity.LoginResult;
import com.xm.demo.retrofit.entity.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Subscriber;

public class MainActivity extends AppCompatActivity {

    String msg = "";
    static final String line = "\n";

    @Bind(R.id.hello)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        getCancelUser();
    }

    private String token;

    // get

    private void login() {

        RetrofitUtils.getInstance().login(new Subscriber<LoginResult>() {
            @Override
            public void onCompleted() {
                print("login onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                print(e.toString());
            }

            @Override
            public void onNext(LoginResult loginResult) {
                token = loginResult.getAccessToken();

                getUser();

                print(new Gson().toJson(loginResult));
            }
        });
    }

    private void getUser() {
        RetrofitUtils.getInstance().getUser(token, new Subscriber<User>() {
            @Override
            public void onCompleted() {
                print("getUser onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                print(e.toString());
            }

            @Override
            public void onNext(User user) {
                print(new Gson().toJson(user));
            }
        });
    }

    private void getUserWithLogin() {
        RetrofitUtils.getInstance().getUserWithLogin(new Subscriber<User>() {
            @Override
            public void onCompleted() {
                print("getUserWithLogin onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                print("getUserWithLogin onError " + e.toString());
            }

            @Override
            public void onNext(User user) {
                print(new Gson().toJson(user));
            }
        });
    }

    // post

    CancelSubscriber<String> subscriber;

    private void getCancelUser() {

        subscriber = new CancelSubscriber<String>() {

            @Override
            public void onStart() {
                super.onStart();
                print("onStart ");
                Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(String s) {
                super.onNext(s);
                Toast.makeText(MainActivity.this, "onNext " + s, Toast.LENGTH_SHORT).show();
                print("onNext " + s);
            }

            @Override
            public void onCancelProgress() {
                super.onCancelProgress();
                Toast.makeText(MainActivity.this, "onCancelProgress", Toast.LENGTH_SHORT).show();
                print("onCancelProgress ");
            }

            @Override
            public void onCompleted() {
                super.onCompleted();
                Toast.makeText(MainActivity.this, "onCompleted", Toast.LENGTH_SHORT).show();
                print("onCompleted ");
            }
        };

        RetrofitUtils.getInstance().getUser(subscriber);

    }

    private void print(String content) {
        msg = textView.getText().toString() + line;
        textView.setText(msg + content);
    }

    @Override
    protected void onDestroy() {
        subscriber.onCancelProgress();
        super.onDestroy();
    }
}
