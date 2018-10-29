package lyxs916.com.rxjava2demo;

import android.annotation.SuppressLint;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lyxs916.com.rxjava2demo.demo.GetRequest_Interface;
import lyxs916.com.rxjava2demo.demo.Translation;
import lyxs916.com.rxjava2demo.utils.LogUtil;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static lyxs916.com.rxjava2demo.MainActivity.TAG;

@SuppressWarnings("unchecked")
@SuppressLint("CheckResult")
public class RxJavaTest {
    /**
     * 简单的使用
     */
    public void text1() {
        //3创建事件
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtil.f().i(TAG, "subscribe");
                e.onNext("132");
                e.onNext("456");
                e.onComplete();
                //注意onComplete() 与 onError() 同时只能调用一个
//                e.onError(new Throwable());
            }
        });

        //2创建观察者
        Observer<String> observer = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                LogUtil.f().i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                LogUtil.f().i(TAG, "onNext：" + s);
                //可采用 Disposable.dispose() 切断观察者 与 被观察者 之间的连接
                disposable.dispose();
            }


            @Override
            public void onError(Throwable e) {
                LogUtil.f().i(TAG, "onError");
            }

            @Override
            public void onComplete() {
                LogUtil.f().i(TAG, "onComplete");
            }
        };
        //3订阅
        observable.subscribe(observer);

    }

    /**
     * 增加切换线程
     */
    public void text2() {
        //3创建事件
        Observable observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                LogUtil.f().i(TAG, "subscribe" + "线程：" + Thread.currentThread().getName());
                e.onNext("132");
                e.onNext("456");
                e.onComplete();
            }
        });
        //切换线程
        observable = observable
                .subscribeOn(Schedulers.io())//运行子线程
                .observeOn(AndroidSchedulers.mainThread());//回调在android 主线程

        //2创建观察者
        Observer<String> observer = new Observer<String>() {
            Disposable disposable;

            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                LogUtil.f().i(TAG, "onSubscribe" + "线程：" + Thread.currentThread().getName());
            }

            @Override
            public void onNext(String s) {
                LogUtil.f().i(TAG, "onNext：" + s + "线程：" + Thread.currentThread().getName());
            }


            @Override
            public void onError(Throwable e) {
                LogUtil.f().i(TAG, "onError" + "线程：" + Thread.currentThread().getName());
            }

            @Override
            public void onComplete() {
                LogUtil.f().i(TAG, "onComplete" + "线程：" + Thread.currentThread().getName());
            }
        };
        //3订阅
        observable.subscribe(observer);
    }

    /**
     * 优雅的实现
     */
    public void text3() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i(TAG, "subscribe" + "线程：" + Thread.currentThread().getName());
                e.onNext("132");
                e.onNext("456");
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe" + "线程：" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext" + "线程：" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError" + "线程：" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete" + "线程：" + Thread.currentThread().getName());
                    }
                });
    }

    /**
     * 网络测试
     */
    public void text4() {
        /*
         * 步骤1：采用interval（）延迟发送
         **/
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                /*
                 * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                 * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                 **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        Log.d(TAG, "第 " + integer + " 次轮询");
                        /*
                         * 步骤3：通过Retrofit发送网络请求
                         **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // b. 创建 网络请求接口 的实例
                        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<Translation> observable = request.getCall();
                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<Translation>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(Translation result) {
                                        // e.接收服务器返回的数据
                                        result.show();
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });


                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe" + "1111111");
            }

            @Override
            public void onNext(Long value) {
                Log.d(TAG, "onNext" + "2222222");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });


    }


    /**
     * 延时测试
     */
    public void text5() {
        Observable.interval(2, 1, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG, "accept" + "1111111");
                    }
                })
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe" + "1111111");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext" + "1111111");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError" + "1111111");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete" + "1111111");
                    }
                });
    }


    /**
     * flatMap
     * <p>
     * 输出结果：
     * text_1.jpeg.png.svg
     * text_1.jpeg.png.svg
     * text_1.jpeg.png.svg
     * text_1.jpeg.png.svg
     */
    public void text6() {
        List<Integer> list = new ArrayList<>();
        list.add(12);
        list.add(12);
        list.add(12);
        list.add(12);


        Observable.just(list).flatMap(new Function<List<Integer>, ObservableSource<List<String>>>() {

            @Override
            public ObservableSource<List<String>> apply(List<Integer> strings) throws Exception {
                List<String> str = new ArrayList<>();
                for (Integer s : strings) {
                    str.add(s + ".jpeg");
                }
                return Observable.just(str);
            }

        }).flatMap(new Function<List<String>, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(List<String> strings) throws Exception {
                List<String> str = new ArrayList<>();
                for (String s : strings) {
                    str.add(s + ".png");
                }
                return Observable.just(str);
            }

        }).flatMap(new Function<List<String>, ObservableSource<List<String>>>() {
            @Override
            public ObservableSource<List<String>> apply(List<String> strings) throws Exception {
                List<String> str = new ArrayList<>();
                for (String s : strings) {
                    str.add(s + ".svg");
                }
                return Observable.just(str);
            }

        }).subscribe(new Consumer<List<String>>() {
            @Override
            public void accept(List<String> strings) throws Exception {
                for (String str : strings) {
                    Log.e("flatmap", str);
                }
            }
        });


    }


    public void text7() {
        Observable.just("Hello", "World")
                .map(new Function<String, Integer>() {
                    @Override
                    public Integer apply(String s) throws Exception {
                        Log.e("flatmap", s);
                        return s.hashCode();
                    }
                })
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        Log.e("flatmap", "AAAA");
                        return "AAAA";
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String str) throws Exception {
                        Log.e("flatmap", "***" + str);
                    }
                });
    }


    public void text8() {
        List<String> list = new ArrayList<>();



    }


}
