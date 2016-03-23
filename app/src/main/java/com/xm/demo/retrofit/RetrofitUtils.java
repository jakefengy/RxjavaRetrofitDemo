package com.xm.demo.retrofit;

import com.google.gson.Gson;
import com.xm.demo.BuildConfig;
import com.xm.demo.retrofit.entity.HttpResult;
import com.xm.demo.retrofit.entity.LoginResult;
import com.xm.demo.retrofit.entity.SaveParams;
import com.xm.demo.retrofit.entity.SaveResult;
import com.xm.demo.retrofit.entity.User;
import com.xm.demo.retrofit.extend.ApiException;
import com.xm.demo.retrofit.extend.CancelSubscriber;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by lvxia on 2016-03-22.
 */
public class RetrofitUtils {

    private String baseUrl = "http://120.24.247.177:8080/open/";

    private Retrofit retrofit;

    private ZeusApis zeusApis;

    private RetrofitUtils() {

        // Log信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // OkHttp3.0的使用方式
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

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

    // Request get

    public void login(Subscriber<LoginResult> callback) {
        zeusApis.login("fengy", "c4ca4238a0b923820dcc509a6f75849b", "1", "")
                .map(new MapFunc1<LoginResult>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);

    }

    public void getUser(String token, Subscriber<User> callback) {
        zeusApis.getUser(token, "1", "535c3b52c78a417e92539a5012fcd1d0", "535c3b52c78a417e92539a5012fcd1d0", "0")
                .map(new MapFunc1<User>())
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
                                .map(new MapFunc1<User>());
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

    // Request post

    public void createUser1(String token, String uid, String org, CancelSubscriber<SaveResult> callback) {
        SaveParams saveParams = new SaveParams();
        saveParams.username = "retrofit bbb";
        saveParams.mobile = "mobile";
        saveParams.email = "email";
        saveParams.company = "company";
        saveParams.dept = "dept";
        saveParams.post = "post";

        String url = "addnewcontact?AccessToken=" + token + "&uid=" + uid + "&organization=" + org;

        zeusApis.createUser1(url, saveParams)
                .subscribeOn(Schedulers.io())
                .map(new MapFunc1<SaveResult>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(callback);

    }

    // Response Pretreatment

    private class MapFunc1<T> implements Func1<HttpResult<T>, T> {

        @Override
        public T call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 0) {
                throw new ApiException(httpResult.getDescription());
            }
            return httpResult.getBody();
        }
    }

    private abstract class FlatMapFunc1<T, R> implements Func1<HttpResult<T>, Observable<R>> {
        @Override
        public Observable<R> call(HttpResult<T> httpResult) {
            if (httpResult.getCode() != 0) {
                throw new ApiException(httpResult.getDescription());
            }
            return flatMapCall(httpResult.getBody());
        }

        abstract Observable<R> flatMapCall(T t);

    }

}
