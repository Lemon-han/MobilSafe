package com.emma.www.myapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {

    private Messenger messenger;
    private Messenger mOutMessenger = new Messenger(new OutgoingHandler());

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(MessageActivity.this, "连接成功！", Toast.LENGTH_SHORT).show();
            messenger = new Messenger(service);
            Message message = new Message();
            message.what = 1;
            message.obj = mOutMessenger;
            try {
                messenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(MessageActivity.this, "连接已经断开！", Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
    }

    /**
     * 绑定服务
     *
     * @param view
     */
    public void click1(View view) {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    /**
     * 发送消息
     *
     * @param view
     */
    public void click2(View view) {
        if (messenger == null) {
            Toast.makeText(this, "服务不可用！", Toast.LENGTH_SHORT).show();
            return;
        }
        Message message = new Message();
        message.obj = "长江长江我是黄河";
        message.what = 0;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    class OutgoingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.d("tag", msg.toString());
        }
    }
}
