package com.xm.demo.retrofit;

import com.xm.demo.retrofit.entity.HttpResult;
import com.xm.demo.retrofit.entity.LoginResult;
import com.xm.demo.retrofit.entity.User;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.internal.schedulers.ScheduledAction;
import rx.schedulers.Schedulers;

/**
 * Created by lvxia on 2016-03-22.
 */
public class RetrofitUtils {

    private String baseUrl = "http://120.24.247.177:8080/open/";

    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();

    private ZeusApis zeusApis;

    private RetrofitUtils() {
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        zeusApis = retrofit.create(ZeusApis.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    //获取单例
    public static RetrofitUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    // Request

    public void login(Subscriber<LoginResult> callback) {
        zeusApis.login("fengy", "c4ca4238a0b923820dcc509a6f75849b1", "1", "")
                .map(new HttpResultFunc<LoginResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);

    }

    public void getUser(String token, Subscriber<User> callback) {
        zeusApis.getUser(token, "1", "535c3b52c78a417e92539a5012fcd1d0", "535c3b52c78a417e92539a5012fcd1d0", "0")
                .map(new HttpResultFunc<User>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

    public Observable<HttpResult<User>> getUser(String token) {
        return zeusApis.getUser(token, "1", "535c3b52c78a417e92539a5012fcd1d0", "535c3b52c78a417e92539a5012fcd1d0", "0");
    }

    public void getUserWithLogin(Subscriber<User> callback) {

        zeusApis.login("fengy", "c4ca4238a0b923820dcc509a6f75849b", "1", "")
                .subscribeOn(Schedulers.io())
                .flatMap(new FlatMapFunc1<LoginResult, User>() {
                    @Override
                    Observable<User> flatMapCall(LoginResult loginResult) {
                        return getUser(loginResult.getAccessToken())
                                .map(new HttpResultFunc<User>());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);

    }

    public void getUser(CancelSubscriber<String> callback) {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onStart();
                    Thread.sleep(5000);
                    subscriber.onNext("Sleep 5s");
                    Thread.sleep(5000);
                    subscriber.onNext("Sleep 10s");
                    Thread.sleep(5000);
                    subscriber.onCompleted();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);
    }

}
