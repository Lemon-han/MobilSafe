package com.emma.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SpUtils {

    private static SharedPreferences sp;

    /**
     * 写入布尔类型变量
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putBoolean(Context context, String key, Boolean value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 读取布尔类型变量
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static Boolean getBoolean(Context context, String key, Boolean defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }
}
