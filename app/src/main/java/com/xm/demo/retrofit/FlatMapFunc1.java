package com.xm.demo.retrofit;

import com.xm.demo.retrofit.entity.HttpResult;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by lvxia on 2016-03-22.
 */
public abstract class FlatMapFunc1<T, R> implements Func1<HttpResult<T>, Observable<R>> {
    @Override
    public Observable<R> call(HttpResult<T> httpResult) {
        if (httpResult.getCode() != 0) {
            throw new ApiException(httpResult.getDescription());
        }
        return flatMapCall(httpResult.getBody());
    }

    abstract Observable<R> flatMapCall(T t);

}
