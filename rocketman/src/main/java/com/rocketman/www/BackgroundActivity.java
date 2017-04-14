package com.rocketman.www;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

public class BackgroundActivity extends Activity {
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			//结束此activity,销毁尾气图片
			finish();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置尾气图片
		setContentView(R.layout.activity_background);
		
		ImageView iv_top = (ImageView) findViewById(R.id.iv_top);
		ImageView iv_bottom = (ImageView) findViewById(R.id.iv_bottom);
		
		//尾气淡入淡出效果,动画是异步操作,并不会去阻塞主线程
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
		alphaAnimation.setDuration(500);
		iv_top.startAnimation(alphaAnimation);
		iv_bottom.startAnimation(alphaAnimation);
		
		//将尾气消失,发送一个延时消息,500毫秒以后结束此activity,
		mHandler.sendEmptyMessageDelayed(0, 1000);
	}
}
