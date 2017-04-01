package com.emma.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/3/30.
 */

public class SpUtil {

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


    /**
     * 写入布尔类型变量
     *
     * @param context
     * @param key
     * @param value
     */
    public static void putString(Context context, String key, String value) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).commit();
    }

    /**
     * 读取布尔类型变量
     *
     * @param context
     * @param key
     * @param defValue
     * @return
     */
    public static String getString(Context context, String key, String defValue) {
        if (sp == null) {
            sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /**
     * 从sp中移除指定节点
     *
     * @param ctx 上下文环境
     * @param key 需要移除节点的名称
     */
    public static void remove(Context ctx, String key) {
        if (sp == null) {
            sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
        }
        sp.edit().remove(key).commit();
    }
}
