package com.xm.demo.rxjava;


import android.util.Log;

import rx.Observable;
import rx.Subscriber;

public class RxjavaTest {

    private static final String TAG = "RxjavaTag";

    public static void main(String[] args) {
        printStr();
    }

    private static void printStr() {

        // Callback
        Subscriber<String> subscriber = new Subscriber<String>() {
            @Override
            public void onCompleted() {
                log("Callback onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                log("Callback onError " + e.toString());
            }

            @Override
            public void onNext(String s) {
                log(s);
            }
        };

        // Observable.create()
        // 创建被观察者
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("Observable.create()  AAA");
                subscriber.onNext("Observable.create()  BBB");
                subscriber.onNext("Observable.create()  CCC");
                subscriber.onCompleted();

            }
        }).subscribe(subscriber); // 建立订阅关系

        // Observable.just()
        Observable.just("Observable.just() 111", "Observable.just() 222", "Observable.just() 333")
                .subscribe(subscriber);

        // Observable.from()
        String[] msgs = {"Observable.from() one", "Observable.from() two", "Observable.from() three"};
        Observable.from(msgs)

                .subscribe(subscriber);

    }

    // print log
    private static void log(String msg) {
        System.out.println(TAG + " : " + msg);
    }


}
