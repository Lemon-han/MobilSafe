package com.emma.mobilesafe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.engine.AddressDao;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.SpUtil;

/**
 * Created by Administrator on 2017/4/3.
 */
public class AddressService extends Service {
    public static final String tag = "AddressService";
    private TelephonyManager mTM;
    private MyPhoneStateListener mPhoneStateListener;
    private final WindowManager.LayoutParams mParams = new WindowManager.LayoutParams();
    private View mViewToast;
    private WindowManager mWM;
    private String mAddress;
    private TextView tv_toast;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            tv_toast.setText(mAddress);
        }

        ;
    };
    private int[] mDrawableIds;

    @Override
    public void onCreate() {
        //第一次开启服务以后,就需要去管理吐司的显示
        //电话状态的监听(服务开启的时候,需要去做监听,关闭的时候电话状态就不需要监听)
        //1,电话管理者对象
        mTM = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //2,监听电话状态
        mPhoneStateListener = new MyPhoneStateListener();
        mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
        //获取窗体对象
        mWM = (WindowManager) getSystemService(WINDOW_SERVICE);

        super.onCreate();
    }

    class MyPhoneStateListener extends PhoneStateListener {
        //3,手动重写,电话状态发生改变会触发的方法
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_IDLE:
                    //空闲状态,没有任何活动(移除吐司)
                    Log.i(tag, "挂断电话,空闲了.......................");
                    //挂断电话的时候窗体需要移除吐司
                    if (mWM != null && mViewToast != null) {
                        mWM.removeView(mViewToast);
                    }
                    break;
                case TelephonyManager.CALL_STATE_OFFHOOK:
                    //摘机状态，至少有个电话活动。该活动或是拨打（dialing）或是通话
                    break;
                case TelephonyManager.CALL_STATE_RINGING:
                    //响铃(展示吐司)
                    Log.i(tag, "响铃了.......................");
                    showToast(incomingNumber);
                    break;
            }
            super.onCallStateChanged(state, incomingNumber);
        }
    }

    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    public void showToast(String incomingNumber) {
        final WindowManager.LayoutParams params = mParams;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE	默认能够被触摸
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
        params.format = PixelFormat.TRANSLUCENT;
        //在响铃的时候显示吐司,和电话类型一致
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        params.setTitle("Toast");

        //指定吐司的所在位置(将吐司指定在左上角)
        params.gravity = Gravity.LEFT + Gravity.TOP;

        //吐司显示效果(吐司布局文件),xml-->view(吐司),将吐司挂在到windowManager窗体上
        mViewToast = View.inflate(this, R.layout.toast_view, null);
        tv_toast = (TextView) mViewToast.findViewById(R.id.tv_toast);

        //从sp中获取色值文字的索引,匹配图片,用作展示
        mDrawableIds = new int[]{
                android.R.color.transparent,
                android.R.color.holo_orange_light,
                android.R.color.holo_blue_bright,
                android.R.color.darker_gray,
                android.R.color.holo_green_light,
        };
        int toastStyleIndex = SpUtil.getInt(getApplicationContext(), ConstantValue.TOAST_STYLE, 0);
        tv_toast.setBackgroundResource(mDrawableIds[toastStyleIndex]);

        //在窗体上挂在一个view(权限)
        mWM.addView(mViewToast, params);

        //获取到了来电号码以后,需要做来电号码查询
        query(incomingNumber);
    }

    private void query(final String incomingNumber) {
        new Thread() {
            public void run() {
                mAddress = AddressDao.getAddress(incomingNumber);
                mHandler.sendEmptyMessage(0);
            }

            ;
        }.start();
    }

    @Override
    public void onDestroy() {
        //取消对电话状态的监听(开启服务的时候监听电话的对象)
        if (mTM != null && mPhoneStateListener != null) {
            mTM.listen(mPhoneStateListener, PhoneStateListener.LISTEN_NONE);
        }
        super.onDestroy();
    }
}