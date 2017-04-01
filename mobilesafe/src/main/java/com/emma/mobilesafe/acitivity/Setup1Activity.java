package com.emma.mobilesafe.acitivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.emma.mobilesafe.R;

public class Setup1Activity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setup1);
	}
	
	public void nextPage(View view){
		Intent intent = new Intent(getApplicationContext(), Setup2Activity.class);
		startActivity(intent);
		
		finish();
		
		//开启平移动画
		overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
	}
}
