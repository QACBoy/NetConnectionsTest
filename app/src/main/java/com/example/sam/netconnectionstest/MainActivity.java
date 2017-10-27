package com.example.sam.netconnectionstest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        if(!NetWork.isNetworkConnected(getApplicationContext())){
            NetWork.setNetworkMethod(MainActivity.this);
            ((TextView)findViewById(R.id.showText)).setText("网络开启失败！！！");
        }else if(!NetWork.isNetworkConnected(getApplicationContext())){
            NetWork.setNetworkMethod(MainActivity.this);
            ((TextView)findViewById(R.id.showText)).setText("网络连接失败！！！");
        }else {
            ((TextView)findViewById(R.id.showText)).setText("网络连接成功！！！");
        }
        super.onStart();
    }


}

