package com.xm.demo.retrofit;

import com.xm.demo.retrofit.entity.HttpResult;
import com.xm.demo.retrofit.entity.LoginResult;
import com.xm.demo.retrofit.entity.User;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by lvxia on 2016-03-22.
 */
public interface ZeusApis {

    @GET("login")
    Observable<HttpResult<LoginResult>> login(
            @Query("username") String username,
            @Query("password") String password,
            @Query("organization") String organization,
            @Query("appkey") String appkey);


    @GET("getuserbyuid")
    Observable<HttpResult<User>> getUser(
            @Query("AccessToken") String token,
            @Query("organization") String organization,
            @Query("uid") String uid,
            @Query("targetuid") String targetuid,
            @Query("timestamp") String timestamp);

}