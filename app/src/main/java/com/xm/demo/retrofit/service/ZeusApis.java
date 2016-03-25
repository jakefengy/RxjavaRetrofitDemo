package com.xm.demo.retrofit.service;

import com.xm.demo.retrofit.entity.HttpResult;
import com.xm.demo.retrofit.entity.LoginResult;
import com.xm.demo.retrofit.entity.SaveParams;
import com.xm.demo.retrofit.entity.SaveResult;
import com.xm.demo.retrofit.entity.User;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;
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

    @Multipart
    @POST
    Observable<HttpResult<SaveResult>> createUser1(
            @Url String url,
            @Query("AccessToken") String token,
            @Query("organization") String organization,
            @Query("uid") String uid,
            @Part("data") RequestBody params);

}
