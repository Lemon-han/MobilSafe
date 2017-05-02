package com.emma.mobilesafe.acitivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.engine.SmsBackUp;

import java.io.File;

public class AToolActivity extends AppCompatActivity {
    private TextView tv_query_phone_address;
    private TextView tv_sms_backup;
    private TextView tv_commonnumber_query;
    private TextView tv_app_lock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atool);

        initPhoneAddress();

        initSmsBackUpt();

        initCommonNumberQuery();

        initAppLock();

    }

    private void initAppLock() {
        tv_app_lock = (TextView) findViewById(R.id.tv_app_lock);
        tv_app_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AppLockActivity.class));
            }
        });
    }

    private void initCommonNumberQuery() {
        tv_commonnumber_query = (TextView) findViewById(R.id.tv_commonnumber_query);
        tv_commonnumber_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),CommonNumberQueryActivity.class));
            }
        });
    }

    private void initSmsBackUpt() {
        tv_sms_backup = (TextView) findViewById(R.id.tv_sms_backup);
        tv_sms_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSmsBackUpDialog();
            }
        });
    }

    private void showSmsBackUpDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.drawable.ic_message_pink_400_24dp);
        progressDialog.setTitle("短信备份");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.show();

        new Thread() {
            @Override
            public void run() {
                String path = Environment.getExternalStorageDirectory().getAbsoluteFile() + File.separator + "sms74.xml";
                SmsBackUp.backup(getApplicationContext(), path, new SmsBackUp.CallBack() {
                    @Override
                    public void setMax(int max) {
                        progressDialog.setMax(max);
                    }

                    @Override
                    public void setProgress(int index) {
                        progressDialog.setProgress(index);
                    }
                });

                progressDialog.dismiss();
            }
        }.start();
    }

    private void initPhoneAddress() {
        tv_query_phone_address = (TextView) findViewById(R.id.tv_query_phone_address);
        tv_query_phone_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), QueryAddressActivity.class));
            }
        });
    }
}
