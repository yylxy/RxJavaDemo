package lyxs916.com.rxjava2demo;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import io.reactivex.functions.Consumer;

import static lyxs916.com.rxjava2demo.MainActivity.TAG;

public class MainActivity3 extends AppCompatActivity implements View.OnClickListener {
//    PollTest pollTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
//        pollTest=  new PollTest();
        findViewById(R.id.item0).setOnClickListener(this);
        findViewById(R.id.item1).setOnClickListener(this);
//        findViewById(R.id.item2).setOnClickListener(this);
//        findViewById(R.id.item3).setOnClickListener(this);
//        findViewById(R.id.item4).setOnClickListener(this);
//        findViewById(R.id.item5).setOnClickListener(this);
//        findViewById(R.id.item6).setOnClickListener(this);
//        findViewById(R.id.item7).setOnClickListener(this);
//        findViewById(R.id.item8).setOnClickListener(this);
//        findViewById(R.id.item9).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item0:
                // 获取activity任务栈
                ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
                finish();
                break;
            case R.id.item1:
                startActivity(new Intent(this, MainActivity2.class));
                break;
            case R.id.item2:
                break;
            case R.id.item3:
                break;
            case R.id.item4:
                break;
            case R.id.item5:
                break;
            case R.id.item6:
                break;
            case R.id.item7:
                break;
            case R.id.item8:
                break;
            case R.id.item9:
                break;
            case R.id.item10:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        pollTest.onDestroy();
    }
}
