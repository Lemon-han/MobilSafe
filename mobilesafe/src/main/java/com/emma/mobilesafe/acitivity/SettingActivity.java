package com.emma.mobilesafe.acitivity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.utils.ConstantVlaue;
import com.emma.mobilesafe.utils.SpUtils;
import com.emma.mobilesafe.view.SettingItemView;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        initUI();

        initUpdate();
    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_settings);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);
        //获取之前的状态
        Boolean open_update = SpUtils.getBoolean(this, ConstantVlaue.OPEN_UPDATE, false);
        siv_update.setCheck(open_update);

        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = siv_update.isCheck();

                siv_update.setCheck(!isCheck);

                SpUtils.putBoolean(getApplicationContext(), ConstantVlaue.OPEN_UPDATE, !isCheck);
            }
        });
    }

}
