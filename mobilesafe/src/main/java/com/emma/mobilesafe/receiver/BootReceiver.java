package com.emma.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.SpUtil;

/**
 * Created by Administrator on 2017/4/1.
 */

public class BootReceiver extends BroadcastReceiver {
    private static final String TAG = "BootReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "restart!");

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String simSerialNumber = tm.getSimSerialNumber();

        String sim_number =SpUtil.getString(context, ConstantValue.SIM_NUMBER,"");
        if(!simSerialNumber.equals(sim_number)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage("5556",null,"sim changes",null,null);
        }
    }
}
