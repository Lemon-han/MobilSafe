package com.emma.mobilesafe.acitivity;

import android.net.TrafficStats;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.emma.mobilesafe.R;

public class TrafficAcitivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_traffic_acitivity);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("流量统计");
        setSupportActionBar(toolbar);

        //下载流量
        long mobileRxBytes = TrafficStats.getMobileRxBytes();
        //上传+下载
        long mobileTxBytes = TrafficStats.getMobileTxBytes();

        //手机+wifi
        long totalRxBytes = TrafficStats.getTotalRxBytes();
        //手机+wifi+上传+下载
        long totalTxBytes = TrafficStats.getTotalTxBytes();

         

    }

}
