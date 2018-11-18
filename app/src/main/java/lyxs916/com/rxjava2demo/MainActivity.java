package lyxs916.com.rxjava2demo;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static String TAG = "Rx_java";
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.item1).setOnClickListener(this);
        findViewById(R.id.item2).setOnClickListener(this);
        findViewById(R.id.item3).setOnClickListener(this);
        findViewById(R.id.item4).setOnClickListener(this);
        findViewById(R.id.item5).setOnClickListener(this);
        findViewById(R.id.item6).setOnClickListener(this);
        findViewById(R.id.item7).setOnClickListener(this);
        findViewById(R.id.item8).setOnClickListener(this);
        findViewById(R.id.item9).setOnClickListener(this);
        findViewById(R.id.item10).setOnClickListener(this);
        findViewById(R.id.item11).setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item1://简化的订约
                // 获取activity任务栈
                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                test1();
                break;
            case R.id.item2://正常的订约
                test2();
                break;
            case R.id.item3://线程切换
                test3();
                break;
            case R.id.item4://map
                test4();
                break;
            case R.id.item5://zip
                test5();
                break;
            case R.id.item6://concat
                test6();
                break;
            case R.id.item7://interval
                test7();
                break;
            case R.id.item8://时间延迟
                test8();
                break;
            case R.id.item9://Flowable 测试1
                test9();
                break;
            case R.id.item10://Flowable 测试2
                test10();
                break;
            
            case R.id.item11:
                startActivity(new Intent(this, MainActivity2.class));
                break;
        }
    }
    
    private void test9() {
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onNext(111);
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onNext(222);
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onNext(333);
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onComplete();
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.tryOnError(new Throwable("错了"));
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
            }
        }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(800);
                        Log.e(TAG, "1111111-------onSubscribe--" + Thread.currentThread().getName());
                    }
                    
                    @Override
                    public void onNext(Integer o) {
                        Log.e(TAG, "1111111-------onNext--" + Thread.currentThread().getName());
                    }
                    
                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "1111111-------onError--" + Thread.currentThread().getName());
                    }
                    
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "1111111-------onComplete--" + Thread.currentThread().getName());
                    }
                });
        
        
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(800);
                Log.e(TAG, "1111111-------onSubscribe--" + Thread.currentThread().getName());
            }
            
            @Override
            public void onNext(Integer o) {
                Log.e(TAG, "1111111-------onNext--" + Thread.currentThread().getName());
            }
            
            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "1111111-------onError--" + Thread.currentThread().getName());
            }
            
            @Override
            public void onComplete() {
                Log.e(TAG, "1111111-------onComplete--" + Thread.currentThread().getName());
            }
        };
        
        
    }
    
    private void test10() {
        Flowable<Integer> flowable = Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onNext(111);
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onNext(222);
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onNext(333);
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.onComplete();
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
                e.tryOnError(new Throwable("错了"));
                Log.e(TAG, "22222-------subscribe--" + Thread.currentThread().getName());
            }
        }, BackpressureStrategy.MISSING)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        
        
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                s.request(800);
                Log.e(TAG, "1111111-------onSubscribe--" + Thread.currentThread().getName());
            }
            
            @Override
            public void onNext(Integer o) {
                Log.e(TAG, "1111111-------onNext--" + Thread.currentThread().getName());
            }
            
            @Override
            public void onError(Throwable t) {
                Log.e(TAG, "1111111-------onError--" + Thread.currentThread().getName());
            }
            
            @Override
            public void onComplete() {
                Log.e(TAG, "1111111-------onComplete--" + Thread.currentThread().getName());
            }
        };
        
        
        flowable.subscribe(subscriber);
        
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：timer 时间延迟         2017/10/27  14:42     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 时间延迟
     */
    @SuppressLint("CheckResult")
    private void test8() {
        Observable.timer(2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // timer 默认在新线程，所以需要切换回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e(TAG, "timer :" + aLong);
                    }
                });
        
        
    }
    
    
    public static String getMD5(String info) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(info.getBytes("UTF-8"));
            byte[] encryption = md5.digest();
            StringBuffer strBuf = new StringBuffer();
            for (int i = 0; i < encryption.length; i++) {
                if (Integer.toHexString(0xff & encryption[i]).length() == 1) {
                    strBuf.append("0").append(Integer.toHexString(0xff & encryption[i]));
                } else {
                    strBuf.append(Integer.toHexString(0xff & encryption[i]));
                }
            }
            
            return strBuf.toString();
        } catch (NoSuchAlgorithmException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：interval  用于间隔时间执行某个操作        2017/10/27  14:37     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
     */
    Disposable disposable = null;
    
    private void test7() {
        
        disposable = Observable.interval(2, 2, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()) // 由于interval默认在新线程，所以我们应该切回主线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        Log.e(TAG, "interval :" + aLong);
                        if (aLong > 5 && disposable != null) {
                            disposable.dispose();
                        }
                    }
                });
        
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：concat          2017/10/26  16:48     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 对于单一的把两个发射器连接成一个发射器，虽然 zip 不能完成，但我们还是可以自力更生，官方提供的 concat 让我们的问题得到了完美解决。
     * <p>
     * 输出结果：
     * 10-26 16:49:00.484 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: concat : 1
     * 10-26 16:49:00.484 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: concat : 2
     * 10-26 16:49:00.485 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: concat : 3
     * 10-26 16:49:00.485 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: concat : 4
     * 10-26 16:49:00.485 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: concat : 5
     * 10-26 16:49:00.485 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: concat : 6
     */
    private void test6() {
        Observable.concat(Observable.just(1, 2, 3), Observable.just(4, 5, 6))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "concat : " + integer + "\n");
                    }
                });
        
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：zip          2017/10/26  16:34     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * zip 专用于合并事件，而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
     * 输出结果：
     * 10-26 16:51:05.760 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: String emit : A
     * 10-26 16:51:05.760 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: String emit : B
     * 10-26 16:51:05.760 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: String emit : C
     * 10-26 16:51:05.760 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: zip : accept : A1
     * 10-26 16:51:05.760 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: Integer emit : 1
     * 10-26 16:51:05.761 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: zip : accept : B2
     * 10-26 16:51:05.761 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: Integer emit : 2
     * 10-26 16:51:05.761 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: zip : accept : C3
     * 10-26 16:51:05.761 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: Integer emit : 3
     * 10-26 16:51:05.761 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: Integer emit : 4
     * 10-26 16:51:05.761 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: Integer emit : 5
     */
    private void test5() {
        Observable.zip(getStringObservable(), getIntegerObservable(), new BiFunction<String, Integer, String>() {
            @Override
            public String apply(@NonNull String s, @NonNull Integer integer) throws Exception {
                return s + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "zip : accept : " + s + "\n");
            }
        });
        
    }
    
    private Observable<String> getStringObservable() {
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<String> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext("A");
                    Log.e(TAG, "String emit : A \n");
                    e.onNext("B");
                    Log.e(TAG, "String emit : B \n");
                    e.onNext("C");
                    Log.e(TAG, "String emit : C \n");
                }
            }
        });
    }
    
    private Observable<Integer> getIntegerObservable() {
        return Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                if (!e.isDisposed()) {
                    e.onNext(1);
                    Log.e(TAG, "Integer emit : 1 \n");
                    e.onNext(2);
                    Log.e(TAG, "Integer emit : 2 \n");
                    e.onNext(3);
                    Log.e(TAG, "Integer emit : 3 \n");
                    e.onNext(4);
                    Log.e(TAG, "Integer emit : 4 \n");
                    e.onNext(5);
                    Log.e(TAG, "Integer emit : 5 \n");
                }
            }
        });
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：map          2017/10/26  16:35     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * map
     * map 基本作用就是将一个 Observable 通过某种函数关系，转换为另一种 Observable，
     * 输出结果：
     * 10-26 16:51:46.469 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: accept : This is result 1
     * 10-26 16:51:46.470 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: accept : This is result 2
     * 10-26 16:51:46.471 6267-6267/lyxs916.com.rxjava2demo E/Rx_java: accept : This is result 3
     */
    @SuppressLint("CheckResult")
    private void test4() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.e(TAG, "accept : " + s + "\n");
            }
            
        });
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：简化的订约          2017/10/26  16:37     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 简化的订约
     */
    @SuppressLint("CheckResult")
    private void test1() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe");
                e.onNext(456);
                e.onError(new Throwable("789"));
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.e(TAG, "accept" + integer);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "Throwable");
            }
        });
        
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：正常的订约          2017/10/26  16:38     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 正常的订约
     */
    private void test2() {
        
        // 第一步：初始化Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@io.reactivex.annotations.NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "subscribe\t" + Thread.currentThread().getName());
                e.onNext(111);
                e.onNext(222);
                e.onNext(333);
                e.onComplete();
                e.tryOnError(new Throwable("错了"));
                
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
//                .observeOn(Schedulers.newThread())//线程的多次切换只有最后一次生效
                .subscribe(new Observer<Integer>() { // 第三步：订阅
                    
                    private int i;
                    private Disposable mDisposable;
                    
                    // 第二步：初始化Observer
                    @Override
                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {//这个方法都是主线程发生
                        mDisposable = d;
                        Log.e(TAG, "onSubscribe\t" + Thread.currentThread().getName());
                    }
                    
                    @Override
                    public void onNext(@io.reactivex.annotations.NonNull Integer integer) {
                        Log.e(TAG, "onNext\t" + integer + Thread.currentThread().getName());
                        i++;
                        if (i == 2) {
                            //在RxJava 2.x 中，新增的Disposable可以做到切断的操作，让Observer观察者不再接收上游事件
//                            mDisposable.dispose();
                        }
                    }
                    
                    @Override
                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                        Log.e(TAG, "onError\t" + Thread.currentThread().getName());
                    }
                    
                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete\t" + Thread.currentThread().getName());
                    }
                });
    }
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                                  说明：线程切换          2017/10/26  16:38     yang
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * 线程切换
     */
    private void test3() {
        /**
         * 简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
         *
         多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
         
         但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。
         */
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                Log.e(TAG, "0000000-------" + Thread.currentThread().getName());
                e.onNext(888);
                e.onComplete();
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "1111111-------" + Thread.currentThread().getName());
                    }
                })
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        Log.e(TAG, "22222222-------" + Thread.currentThread().getName());
                    }
                });
        
        
    }
    
    
}
