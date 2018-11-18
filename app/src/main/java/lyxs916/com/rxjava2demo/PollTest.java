package lyxs916.com.rxjava2demo;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import javax.sql.ConnectionPoolDataSource;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import lyxs916.com.rxjava2demo.demo.GetRequest_Interface;
import lyxs916.com.rxjava2demo.demo.Translation;
import lyxs916.com.rxjava2demo.utils.LogUtil;
import lyxs916.com.rxjava2demo.utils.RetrofitUtils;

@SuppressLint("CheckResult")
public class PollTest {
    Disposable disposable;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void polling() {
        Observable<Translation> observable = RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall();


        Observable.interval(0, 2, TimeUnit.SECONDS).doOnNext(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall().subscribe(new Consumer<Translation>() {
                    @Override
                    public void accept(Translation translation) throws Exception {


                        LogUtil.e("polling", translation.getOut());


                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                });

            }


        }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {
                disposable = d;
                compositeDisposable.add(d);
                LogUtil.e("polling", "WWWWWWW");
            }

            @Override
            public void onNext(Long aLong) {
                if (aLong == 20) {
                    disposable.dispose();

                }
                LogUtil.e("polling", aLong + "");

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e("polling", "onError");
            }

            @Override
            public void onComplete() {
                LogUtil.e("polling", "onComplete");
            }
        });
    }


    int count;

    public void polling2() {
        compositeDisposable.clear();
        Observable<Translation> observable = RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall();

        observable.delay(1, TimeUnit.SECONDS)

                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Translation translation) {
                        count++;
                        if (count%2 == 0) {
                            LogUtil.e("polling", translation.getOut() + count);
//                            compositeDisposable.dispose();
                            result();
                            return;
                        }
                        polling2();

                        LogUtil.e("polling", translation.getOut() + count + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 接口的连续调用
     */
    private void result() {

        Observable<Translation> observable = RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall();

        observable.delay(1, TimeUnit.SECONDS).subscribeOn(Schedulers.io()).observeOn(Schedulers.io())

                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(Translation translation) throws Exception {
                        LogUtil.e("polling", translation.getOut() + "AAAA" + Thread.currentThread().getName());

                        return RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall();
                    }

                }).observeOn(Schedulers.io())

                .flatMap(new Function<Translation, ObservableSource<Translation>>() {
                    @Override
                    public ObservableSource<Translation> apply(Translation translation) throws Exception {
                        LogUtil.e("polling", translation.getOut() + "BBBB" + Thread.currentThread().getName());
                        return RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall();
                    }
                }).observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Observer<Translation>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Translation translation) {
                        LogUtil.e("polling", translation.getOut() + "CCCCC" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("polling", "throwable" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public void onDestroy() {


        compositeDisposable.dispose();
        compositeDisposable.clear();
    }

}
