package com.rocketman.www;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button bt_start = (Button) findViewById(R.id.bt_start);
		Button bt_close = (Button) findViewById(R.id.bt_close);
		
		bt_start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//开启服务
				startService(new Intent(getApplicationContext(), RocketService.class));
				//关闭当前界面
				finish();
			}
		});
		bt_close.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//开启服务
				stopService(new Intent(getApplicationContext(), RocketService.class));
				//关闭当前界面
				finish();
			}
		});
	}
}
