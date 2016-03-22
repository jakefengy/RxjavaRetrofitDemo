package com.xm.demo.retrofit;

import com.xm.demo.retrofit.entity.HttpResult;

import rx.functions.Func1;

/**
 * Created by lvxia on 2016-03-22.
 */
public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {

    @Override
    public T call(HttpResult<T> httpResult) {
        if (httpResult.getCode() != 0) {
            throw new ApiException(httpResult.getDescription());
        }
        return httpResult.getBody();
    }
}