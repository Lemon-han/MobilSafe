package com.emma.mobilesafe.acitivity;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.SpUtil;
import com.emma.mobilesafe.utils.ToastUtil;
import com.emma.mobilesafe.view.SettingItemView;

public class Setup2Activity extends BaseSetUpAppCompatActivity {
    private SettingItemView siv_sim_bound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup2);

        initUI();
    }


    private void initUI() {
        siv_sim_bound = (SettingItemView) findViewById(R.id.siv_sim_bound);
        //1,回显(读取已有的绑定状态,用作显示,sp中是否存储了sim卡的序列号)
        String sim_number = SpUtil.getString(this, ConstantValue.SIM_NUMBER, "");
        //2,判断是否序列卡号为""
        if (TextUtils.isEmpty(sim_number)) {
            siv_sim_bound.setCheck(false);
        } else {
            siv_sim_bound.setCheck(true);
        }

        siv_sim_bound.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //3,获取原有的状态
                boolean isCheck = siv_sim_bound.isCheck();
                //4,将原有状态取反
                //5,状态设置给当前条目
                siv_sim_bound.setCheck(!isCheck);
                if (!isCheck) {
                    //6.0以上需要进行权限处理
                    if (ContextCompat.checkSelfPermission(Setup2Activity.this, Manifest.permission.READ_PHONE_STATE) !=
                            PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(Setup2Activity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        //6,存储(序列卡号)
                        getAndSaveSimSerialNumber();
                    }


                } else {
                    //7,将存储序列卡号的节点,从sp中删除掉
                    SpUtil.remove(getApplicationContext(), ConstantValue.SIM_NUMBER);
                }
            }


        });
    }

    public void getAndSaveSimSerialNumber() {

        //6.1获取sim卡序列号TelephoneManager
        TelephonyManager manager = (TelephonyManager)
                getSystemService(Context.TELEPHONY_SERVICE);
        //6.2获取sim卡的序列卡号
        String simSerialNumber = manager.getSimSerialNumber();
        //6.3存储
        SpUtil.putString(getApplicationContext(), ConstantValue.SIM_NUMBER, simSerialNumber);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getAndSaveSimSerialNumber();
                } else {
                    ToastUtil.show(this, "You denied the permission");
                }
                break;
            default:
        }
    }

    @Override
    protected void showPrePage() {
        Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
        startActivity(intent);

        finish();

        overridePendingTransition(R.anim.pre_in_anim, R.anim.pre_out_anim);
    }

    @Override
    protected void showNextPage() {
        String serialNumber = SpUtil.getString(this, ConstantValue.SIM_NUMBER, "");
        if (!TextUtils.isEmpty(serialNumber)) {
            Intent intent = new Intent(getApplicationContext(), Setup3Activity.class);
            startActivity(intent);

            finish();

            overridePendingTransition(R.anim.next_in_anim, R.anim.next_out_anim);
        } else {
            ToastUtil.show(this, "请绑定sim卡");
        }
    }

}
