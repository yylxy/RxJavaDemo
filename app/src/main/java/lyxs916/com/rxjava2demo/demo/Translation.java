package lyxs916.com.rxjava2demo.demo;

import android.util.Log;

/**
 * Created by Carson_Ho on 17/9/8.
 */

public class Translation {


    private int status;

    public content content;
    private static class content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private int errNo;
    }

    //定义 输出返回数据 的方法
    public void show() {
        Log.d("RxJava", content.out );
    }

    public String getOut(){
        return content.out;
    }
}
