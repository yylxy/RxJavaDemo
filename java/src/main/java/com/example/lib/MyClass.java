package com.example.lib;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

@SuppressWarnings("all")
public class MyClass {

    public static void main(String[] args) {
        //        map();
        //        flatMap();
        new RxJava().login();
    }

    private static void map() {
        //Integer
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
            //Strig
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return String.format("The %s time", integer);
            }
            //String 叠加
        }).map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return String.format("%s , He is second time", s);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println(s);
            }
        });

    }

    private static void flatMap() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onComplete();
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer integer) throws Exception {
                List<String> list = new ArrayList();
                list.add("He is " + integer);
                list.add("He is " + integer);
                list.add("He is " + integer);

                return Observable.fromIterable(list).delay(10, TimeUnit.MICROSECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                System.out.println(str);
            }
        });
    }


    static class RxJava {
        public void login() {
            renoteRegister().doOnNext(new Consumer<String>() {//开始注册
                @Override
                public void accept(String s) throws Exception {//注册成功回调
                    System.out.println(s);
                    System.out.println(Thread.currentThread().getName());
                }

            }).flatMap(new Function<String, ObservableSource<String>>() {
                @Override
                public ObservableSource<String> apply(String s) throws Exception {
                    System.out.println(s + "--开始登录");
                    System.out.println(Thread.currentThread().getName());
                    return renoteLogin();//调用登录
                }
            }).subscribe(new Consumer<String>() {
                @Override
                public void accept(String s) throws Exception {//登录回调
                    System.out.println(s);
                    System.out.println(Thread.currentThread().getName());
                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    System.out.println(throwable.getMessage());
                    System.out.println(Thread.currentThread().getName());
                }
            });
        }

        //远程登录
        public Observable<String> renoteLogin() {
            return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("登录成功");
                    emitter.onComplete();
                }
            });
        }

        //远程注册
        public Observable<String> renoteRegister() {
            return Observable.create(new ObservableOnSubscribe<String>() {
                @Override
                public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                    emitter.onNext("注册成功");
                    emitter.onComplete();
                }
            });
        }
    }


}
