package com.test.niuhongbin.handlerdemo;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tv;
    private int count=0;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    //处理消息
                    int value = msg.arg1;
                    tv.setText("当前的值="+value);
                    break;
            };
        };
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv_01);

        //Android线程：主线程（UI线程）+工作线程（work线程）
        //耗时操作：访问网络（下载json，图片，多媒体文件等到）；访问数据库等
        //主线程主要负责UI的更新（界面的操作），不能进行耗时操作
        //工作线程负责进行耗时的操作，但是不能进行UI的更新
    }

    //发送消息
    public void SendMsg(View view) {
        //模拟一个耗时操作,--->工作线程进行
        new Thread(){
            public void run(){
                while (count<1000){
                    count++;

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Message msg = Message.obtain();
                    //消息的标记
                    msg.what = 1;
                    //传递arg1
                    msg.arg1 =count;
//                    msg.obj="传递object的数据"
                    handler.sendMessage(msg);

                }
            }
        }.start();
    }
}
