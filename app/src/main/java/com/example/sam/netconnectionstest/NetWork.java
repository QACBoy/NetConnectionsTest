package com.example.sam.netconnectionstest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.util.Log;

/**
 * Created by Sam on 2017/10/27.
 */

/*
   需要添加的权限有
        <!--允许访问网络状态的权限-->
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <!--允许修改网络状态的权限-->
         <uses-permission android:name="android.permission.CHANGE_NETWORK_STATE"/>
        <!--允许修改 WIFI 状态的权限-->
        <uses-permission android:name="android.permission.CHANGE_WIFI_STATE"/>
        <!--允许访问 WIFI 状态的权限-->
        <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
 */

public class NetWork {
    static String TAG="sam001";
    static String msg = null;

    // 判断移动网络是否开启
    private static boolean isNetEnabled(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
        } else {
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();
            if (networkInfo != null&&networkInfo.length>0) {
                for (int i = 0; i < networkInfo.length; i++) {
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED) {
                        Log.d(TAG, "移动网络已经开启");
                        return true;
                    }
                }
            }
        }
        Log.d(TAG, "移动网络还未开启");
        return false;
    }

    // 判断WIFI网络是否开启
    private static boolean isWifiEnabled(Context context) {
        WifiManager wifiManager = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            Log.d(TAG, "Wifi网络已经开启");
            return true;
        }
        Log.d(TAG, "Wifi网络还未开启");
        return false;
    }

    // 判断移动网络是否连接成功
    private static boolean isNetContected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (connectivityManager != null && info != null && info.isConnected()) {
            Log.d(TAG, "移动网络连接成功");
            return true;
        }
        Log.d(TAG, "移动网络连接失败");
        return false;
    }

    // 判断WIFI是否连接成功
    private static boolean isWifiContected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null && info.isConnected()) {
            Log.d(TAG, "Wifi网络连接成功");
            return true;
        }
        Log.d(TAG, "Wifi网络连接失败");
        return false;
    }

   // 判断移动网络和WIFI是否开启
    public static Boolean isNetWorkEnabled(final Context context) {
        if(isNetEnabled(context)||isWifiEnabled(context))
        {
            Log.d(TAG, "网络已经开启");
            return true;
        }
        else{
            msg = "网络未开启,请进行设置！";

            Log.d(TAG, "网络还未开启");
            return false;
        }
    }

    // 判断移动网络和WIFI是否连接成功
    public static boolean isNetworkConnected(final Context context) {
        if(isWifiContected(context)||isNetContected(context)){
            Log.d(TAG, "网络连接成功");
            return true;
        }else{
            msg = "网络连接失败,请检查网络设置！";

            Log.d(TAG, "网络连接失败");
            return false;
        }
    }

    // 打开设置网络界面
    public static void setNetworkMethod(final Context context){

        //提示对话框
        final AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("网络设置提示").setMessage( msg ).setPositiveButton("设置", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Intent intent = null;
                //判断手机系统的版本  即API大于10 就是3.0或以上版本
                if(Build.VERSION.SDK_INT>10){
                    //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
                    intent = new Intent(Settings.ACTION_SETTINGS);
                } else {
                    intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                }
                context.startActivity(intent);
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setTitle("警告").setMessage("因为当前网络不可用，会引起部分功能不能正常使用！").setPositiveButton("检查网络", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        Intent intent=null;
                        //判断手机系统的版本  即API大于10 就是3.0或以上版本
                        if(Build.VERSION.SDK_INT>10){
                            //3.0以上打开设置界面，也可以直接用ACTION_WIRELESS_SETTINGS打开到wifi界面
                            intent = new Intent(Settings.ACTION_SETTINGS);
                        } else {
                            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                        }
                        context.startActivity(intent);
                    }
                }).setNegativeButton("继续使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        }).setCancelable(false).show();
    }
}
