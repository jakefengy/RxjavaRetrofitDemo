package com.xm.demo.rxjava;


import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;

public class RxjavaTest {

    private static final String TAG = "RxjavaTag";

    public static void main(String[] args) {
        flatMap();
    }

    // 创建Observable 并展示三种打印方式  同线程。
    private static void printStr() {

        // Callback
        final Subscriber<String> subscriber = new Subscriber<String>() {
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
        Action0 onCompleteAction = new Action0() {
            @Override
            public void call() {
                log("Callback onCompleteAction");
            }
        };

        Action1<String> onNextAction = new Action1<String>() {
            @Override
            public void call(String s) {
                log("Callback onNextAction " + s);
            }
        };

        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                log("Callback onErrorAction");
            }
        };

        Observable.just("Observable.just() 111", "Observable.just() 222", "Observable.just() 333")
                .subscribe(onNextAction, onErrorAction, onCompleteAction);

        // Observable.from()
        String[] msgs = {"Observable.from() one", "Observable.from() two", "Observable.from() three"};
        Observable.from(msgs)
                .subscribe(subscriber);

    }

    // 变换 String to Integer map 1：1
    private static void map() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("0");
            }
        }).map(new Func1<String, Integer>() {
            @Override
            public Integer call(String s) {
                return Integer.valueOf(s);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                log("This is create Integer " + integer);
            }
        });

        Observable.just("1", "2", "3")
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return Integer.valueOf(s);
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        log("This is just Integer " + integer);
                    }
                });

    }

    // 变换 flatMap 1：N
    private static void flatMap() {
        String[] names = {"A-a-r-o-n", "K-o-b-e"};

        Observable.from(names)
                .flatMap(new Func1<String, Observable<String>>() {
                    @Override
                    public Observable<String> call(String s) {
                        return Observable.from(s.split("-"));
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        log("This is flatMap String " + s);
                    }
                });

    }

    // print log
    private static void log(String msg) {
        System.out.println(TAG + " : " + msg);
    }


}
