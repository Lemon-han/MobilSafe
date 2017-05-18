package com.emma.www.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class MessengerService extends Service {

    private Messenger messenger = new Messenger(new IncomingHandler());
    private Messenger mActivityMessenger;


    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = messenger.getBinder();
        return binder;
    }

    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.d("tag", msg.toString());
                    if (mActivityMessenger != null) {
                        Message message = new Message();
                        message.what = 2;
                        message.obj = "地瓜地瓜我是土豆";
                        try {
                            mActivityMessenger.send(message);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                case 1:
                    mActivityMessenger = (Messenger) msg.obj;
                    Log.d("tag", "已经获取到 Activity 发送了的 Messenger 对象");
                    break;

                default:
                    break;

            }
        }
    }
}
