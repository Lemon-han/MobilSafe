package com.emma.mobilesafe.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/3/28.
 */

public class ToastUtil {
    public static void show(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
