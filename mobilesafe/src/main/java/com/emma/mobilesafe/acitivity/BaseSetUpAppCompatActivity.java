package com.emma.mobilesafe.acitivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/4/1.
 */
public abstract class BaseSetUpAppCompatActivity extends AppCompatActivity {
    private GestureDetector gestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

                if (e1.getX() - e2.getX() > 0) {
                    //下一页
                    showNextPage();

                }
                if (e1.getX() - e2.getX() < 0) {
                    showPrePage();
                }

                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }


    //1 监听屏幕上相应事件的类型
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }


    protected abstract void showPrePage();

    protected abstract void showNextPage();


    public void nextPage(View view) {
        showNextPage();
    }

    public void prePage(View view) {
        showPrePage();
    }

}
