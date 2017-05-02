package com.emma.mobilesafe.global;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2017/5/2.
 */

public class MyApplication extends Application {
    protected static final String tag = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();

        //获取全局异常
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable ex) {
                ex.printStackTrace();
                Log.i(tag, "捕获了一个异常");

                //将捕获的异常存储到sd卡中
                String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "error74.log";
                File file = new File(path);
                try {
                    PrintWriter printWriter = new PrintWriter(file);
                    ex.printStackTrace(printWriter);
                    printWriter.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //上传公司的服务器
                //结束应用
                System.exit(0);
            }
        });
    }
}
