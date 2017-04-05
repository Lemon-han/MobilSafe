package com.emma.mobilesafe.receiver;

import android.app.admin.DevicePolicyManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.service.LocationService;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.SpUtil;
import com.emma.mobilesafe.utils.ToastUtil;

/**
 * Created by Administrator on 2017/4/2.
 */

public class SmsReceiver extends BroadcastReceiver {

    private ComponentName mDeviceAdminSample;
    private DevicePolicyManager mDPM;

    @Override
    public void onReceive(Context context, Intent intent) {
        //组件对象可以作为是否激活的判断标志
        mDeviceAdminSample = new ComponentName(context, DeviceAdmin.class);
        mDPM = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);

        Boolean open_security = SpUtil.getBoolean(context, ConstantValue.OPEN_SECURITY, false);

        if (open_security) {
            Object[] objects = (Object[]) intent.getExtras().get("pdus");

            for (Object object : objects) {
                SmsMessage sms = SmsMessage.createFromPdu((byte[]) object);

                String orginatingAddress = sms.getOriginatingAddress();
                String messageBody = sms.getMessageBody();

                if (messageBody.contains("#*alarm*#")) {
                    MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw.ylzs);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
                if (messageBody.contains("#*location*#")) {
                    context.startService(new Intent(context, LocationService.class));
                }
                if (messageBody.contains("#*lockscreen*#")) {
                    //是否开启的判断
                    if (mDPM.isAdminActive(mDeviceAdminSample)) {
                        //激活--->锁屏
                        mDPM.lockNow();
                        //锁屏同时去设置密码
                        mDPM.resetPassword("123", 0);
                    } else {
                        ToastUtil.show(context,"请先激活");
                    }

                }
                if (messageBody.contains("#*wipedata*#")) {
                    if (mDPM.isAdminActive(mDeviceAdminSample)) {
                        mDPM.wipeData(0);//手机数据
//					mDPM.wipeData(DevicePolicyManager.WIPE_EXTERNAL_STORAGE);//手机sd卡数据
                    } else {
                        ToastUtil.show(context,"请先激活");
                    }
                }
            }
        }

    }
}
