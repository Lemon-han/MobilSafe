package com.emma.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.emma.mobilesafe.R;

/**
 * Created by Administrator on 2017/3/29.
 */

public class SettingItemView extends RelativeLayout {
    private Switch open;

    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //xml--->view	将设置界面的一个条目转换成view对象,直接添加到了当前SettingItemView对应的view中
        View.inflate(context, R.layout.setting_item_view, this);
        open = (Switch) findViewById(R.id.settting_item_switch);


        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {

    }


    public boolean isCheck() {
        return open.isChecked();
    }

    public void setCheck(Boolean isCheck) {
        open.setChecked(isCheck);
        if (isCheck) {
            open.setText("自动更新已开启");
        } else {
            open.setText("自动更新已关闭");
        }
    }
}
