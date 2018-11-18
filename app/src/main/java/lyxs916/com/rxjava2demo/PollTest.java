package lyxs916.com.rxjava2demo;

import android.annotation.SuppressLint;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import lyxs916.com.rxjava2demo.demo.GetRequest_Interface;
import lyxs916.com.rxjava2demo.demo.Translation;
import lyxs916.com.rxjava2demo.utils.RetrofitUtils;

public class PollTest {
    Disposable disposable;
    @SuppressLint("CheckResult")
    public void polling() {
        Observable<Translation> observable = RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall();


        Observable.interval( 0,2,TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        RetrofitUtils.getInstance().create(GetRequest_Interface.class).getCall().subscribe(new Consumer<Translation>() {
                            @Override
                            public void accept(Translation translation) throws Exception {
//                                disposable.dispose();

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
                disposable=d;
            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
