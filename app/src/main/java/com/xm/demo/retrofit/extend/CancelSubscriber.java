package com.xm.demo.retrofit.extend;

import android.util.Log;

import com.google.gson.Gson;

import rx.Subscriber;

/**
 * Created by lvxia on 2016-03-23.
 */
public abstract class CancelSubscriber<T> extends Subscriber<T> implements HttpCancelListener {

    @Override
    public void onStart() {
        super.onStart();
        Log.i("CancelSubscriberTag", "onStart");
    }

    @Override
    public void onCancelProgress() {
        if (!isUnsubscribed()) {
            Log.i("CancelSubscriberTag", "onCancelProgress.unsubscribe");
            unsubscribe();
        }
    }

    @Override
    public void onNext(T t) {
        Log.i("CancelSubscriberTag", "onNext " + new Gson().toJson(t));
        onEventNext(t);
    }

    @Override
    public void onCompleted() {
        Log.i("CancelSubscriberTag", "onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        Log.i("CancelSubscriberTag", "onError " + e.toString());
    }

    public abstract void onEventNext(T t);

}
