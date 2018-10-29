package lyxs916.com.rxjava2demo;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static lyxs916.com.rxjava2demo.MainActivity.TAG;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {
    RxJavaTest rxJava;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        rxJava = new RxJavaTest();
        findViewById(R.id.item0).setOnClickListener(this);
        findViewById(R.id.item1).setOnClickListener(this);
        findViewById(R.id.item2).setOnClickListener(this);
        findViewById(R.id.item3).setOnClickListener(this);
        findViewById(R.id.item4).setOnClickListener(this);
        findViewById(R.id.item5).setOnClickListener(this);
        findViewById(R.id.item6).setOnClickListener(this);
        findViewById(R.id.item7).setOnClickListener(this);
//        findViewById(R.id.item8).setOnClickListener(this);
//        findViewById(R.id.item9).setOnClickListener(this);
//        findViewById(R.id.item10).setOnClickListener(this);
//        findViewById(R.id.item11).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item0:
                finish();
                break;
            case R.id.item1:
                rxJava.text1();//简化的订约
                break;
            case R.id.item2:
                rxJava.text2();//线程的切换
                break;
            case R.id.item3:
                rxJava.text3();//优雅的实现
                break;
            case R.id.item4:
                rxJava.text4();//网络测试
                break;
            case R.id.item5:
                rxJava.text5();//延时测试
                break;
            case R.id.item6:
                rxJava.text6();//flatMap
                break;
            case R.id.item7:
                rxJava.text7();//map
                break;
            case R.id.item8:
                break;
            case R.id.item9:
                break;
            case R.id.item10:
                break;


        }
    }


}
