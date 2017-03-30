package com.emma.mobilesafe.common.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Administrator on 2017/3/30.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {


    private SparseArray<View> mViews;

    public abstract int getLayoutId();
    public abstract void initUI();
    public abstract void initListener();
    public abstract void initData();
    public abstract void processClick(View v);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViews = new SparseArray<>();
        setContentView(getLayoutId());
        initUI();
        initListener();
        initData();
    }

    @Override
    public void onClick(View v) {
        v.setOnClickListener(this);
    }
}
