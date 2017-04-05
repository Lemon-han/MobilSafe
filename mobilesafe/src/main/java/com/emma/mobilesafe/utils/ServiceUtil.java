package com.emma.mobilesafe.utils;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2017/4/3.
 */

public class ServiceUtil {

    public static boolean isRunning(Context context, String serviceName) {

        ActivityManager mAM = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = mAM.getRunningServices(1000);

        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            if (serviceName.equals(runningServiceInfo.service.getClassName()))
                return true;
        }

        return false;
    }
}
