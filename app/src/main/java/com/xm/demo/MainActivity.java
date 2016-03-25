package com.xm.demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.xm.demo.retrofit.entity.SaveResult;
import com.xm.demo.retrofit.extend.CancelSubscriber;
import com.xm.demo.retrofit.RetrofitUtils;
import com.xm.demo.retrofit.entity.LoginResult;
import com.xm.demo.retrofit.entity.User;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Retrofit;
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
        login();
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

                createUser1(loginResult.getAccessToken());

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

    CancelSubscriber<String> subscriber;

    private void getCancelUser() {

        subscriber = new CancelSubscriber<String>() {
            @Override
            public void onEventNext(String s) {
                print("Test Cancel " + s);
            }
        };

        RetrofitUtils.getInstance().getUser(subscriber);

    }

    // post

    private void createUser1(String token) {
        CancelSubscriber<SaveResult> subscriber = new CancelSubscriber<SaveResult>() {
            @Override
            public void onEventNext(SaveResult saveResult) {
                print("createUser1 onCompleted " + saveResult.getContactid());
            }
        };

        RetrofitUtils.getInstance().createUser1(token, subscriber);
    }

    // tools

    private void print(String content) {
        msg = textView.getText().toString() + line;
        textView.setText(msg + content);
    }

    @Override
    protected void onDestroy() {
        if (subscriber != null)
            subscriber.onCancelProgress();
        super.onDestroy();
    }
}
