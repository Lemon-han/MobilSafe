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

    public static String NAMESPACE = "http://schemas.android.com/apk/res/com.emma.mobilesafe";
    private String mDestitle;
    private String mDesoff;
    private String mDeson;

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

        open.setText(mDestitle);

    }

    private void initAttrs(AttributeSet attrs) {
        mDestitle = attrs.getAttributeValue(NAMESPACE, "destitle");
        mDesoff = attrs.getAttributeValue(NAMESPACE, "desoff");
        mDeson = attrs.getAttributeValue(NAMESPACE, "deson");
    }


    public boolean isCheck() {
        return open.isChecked();
    }

    public void setCheck(Boolean isCheck) {
        open.setChecked(isCheck);
        if (isCheck) {
            open.setText(mDeson);
        } else {
            open.setText(mDesoff);
        }
    }
}
