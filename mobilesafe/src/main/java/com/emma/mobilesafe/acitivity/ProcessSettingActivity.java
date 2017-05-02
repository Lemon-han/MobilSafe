package com.emma.mobilesafe.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.service.LockScreenService;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.ServiceUtil;
import com.emma.mobilesafe.utils.SpUtil;
import com.emma.mobilesafe.view.SettingItemView;

public class ProcessSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        initSystemShow();
        initLockScreenClear();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void initLockScreenClear() {
        final SettingItemView cb_lock_clear = (SettingItemView) findViewById(R.id.cb_lock_clear);

        boolean isRunning = ServiceUtil.isRunning(this, "com.emma.mobilesafe.service.LockScreenService");
        cb_lock_clear.setCheck(isRunning);

        cb_lock_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCheck = cb_lock_clear.isCheck();

                cb_lock_clear.setCheck(!isCheck);

                if (isCheck) {
                    startService(new Intent(getApplicationContext(), LockScreenService.class));
                } else {
                    stopService(new Intent(getApplicationContext(), LockScreenService.class));
                }

            }
        });
    }

    private void initSystemShow() {
        final SettingItemView cb_show_system = (SettingItemView) findViewById(R.id.cb_show_system);

        boolean open_update = SpUtil.getBoolean(this, ConstantValue.SHOW_SYSTEM, false);

        cb_show_system.setCheck(open_update);

        cb_show_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isCheck = cb_show_system.isCheck();

                cb_show_system.setCheck(!isCheck);

                SpUtil.putBoolean(getApplicationContext(), ConstantValue.SHOW_SYSTEM, !isCheck);
            }
        });
    }

}
