package com.emma.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/3/29.
 */

public class FocusTextView extends TextView {


    //java中创建
    public FocusTextView(Context context) {
        super(context);
    }

    //系统调用
    public FocusTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    //带属性+上下文环境构造方法+布局文件中定义样式文件构造方法
    public FocusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
