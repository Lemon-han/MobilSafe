package com.emma.mobilesafe.acitivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.service.AddressService;
import com.emma.mobilesafe.service.BlackNumberService;
import com.emma.mobilesafe.service.WatchDogService;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.ServiceUtil;
import com.emma.mobilesafe.utils.SpUtil;
import com.emma.mobilesafe.utils.ToastUtil;
import com.emma.mobilesafe.view.SettingClickView;
import com.emma.mobilesafe.view.SettingItemView;


public class SettingActivity extends AppCompatActivity {
    private String[] mToastStyleDes;
    private int mToastStyle;
    private SettingClickView scv_toast_style;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle("设置中心");
//        setSupportActionBar(toolbar);

        initUpdate();
        initAddress();
        initToastStyle();
        initLocation();
        initAppLock();

        //6.0以上需要进行权限处理
        if (ContextCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SettingActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            initBlacknumber();
        }
    }

    private void initAppLock() {
        final SettingItemView siv_app_lock = (SettingItemView) findViewById(R.id.siv_app_lock);
        boolean isRunning = ServiceUtil.isRunning(this, "com.emma.mobilesafe.service.WatchDogService");
        siv_app_lock.setCheck(isRunning);

        siv_app_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = siv_app_lock.isCheck();
                siv_app_lock.setCheck(!isCheck);
                if (!isCheck) {
                    //开启服务
                    startService(new Intent(getApplicationContext(), WatchDogService.class));
                } else {
                    //关闭服务
                    stopService(new Intent(getApplicationContext(), WatchDogService.class));
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initBlacknumber();
                } else {
                    ToastUtil.show(this, "You denied the permission");
                }
                break;
            default:
        }
    }

    private void initBlacknumber() {

        final SettingItemView siv_blacknumber = (SettingItemView) findViewById(R.id.siv_blacknumber);
        boolean isRunning = ServiceUtil.isRunning(this, "com.emma.mobilesafe.service.BlackNumberService");
        siv_blacknumber.setCheck(isRunning);

        siv_blacknumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isCheck = siv_blacknumber.isCheck();
                siv_blacknumber.setCheck(!isCheck);

                if (!isCheck) {
                    //开启服务
                    startService(new Intent(getApplicationContext(), BlackNumberService.class));
                } else {
                    //关闭服务
                    stopService(new Intent(getApplicationContext(), BlackNumberService.class));

                }
            }
        });
    }

    /**
     * 双击居中view所在屏幕位置的处理方法
     */
    private void initLocation() {
        SettingClickView scv_location = (SettingClickView) findViewById(R.id.scv_location);
        scv_location.setTitle("归属地提示框的位置");
        scv_location.setDes("设置归属地提示框的位置");
        scv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ToastLocationActivity.class));
            }
        });
    }

    private void initToastStyle() {
        scv_toast_style = (SettingClickView) findViewById(R.id.scv_toast_style);
        //话述(产品)
        scv_toast_style.setTitle("设置归属地显示风格");
        //1,创建描述文字所在的string类型数组
        mToastStyleDes = new String[]{"透明", "橙色", "蓝色", "灰色", "绿色"};
        //2,SP获取吐司显示样式的索引值(int),用于获取描述文字

        mToastStyle = SpUtil.getInt(this, ConstantValue.TOAST_STYLE, 0);

        //3,通过索引,获取字符串数组中的文字,显示给描述内容控件
        scv_toast_style.setDes(mToastStyleDes[mToastStyle]);
        //4,监听点击事件,弹出对话框
        scv_toast_style.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //5,显示吐司样式的对话框
                showToastStyleDialog();
            }
        });
    }

    /**
     * 创建选中显示样式的对话框
     */
    protected void showToastStyleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.ic_star_half_pink_400_24dp);
        builder.setTitle("请选择归属地样式");
        //选择单个条目事件监听
        /*
         * 1:string类型的数组描述颜色文字数组
		 * 2:弹出对画框的时候的选中条目索引值
		 * 3:点击某一个条目后触发的点击事件
		 * */
        builder.setSingleChoiceItems(mToastStyleDes, mToastStyle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {//which选中的索引值
                //(1,记录选中的索引值,2,关闭对话框,3,显示选中色值文字)
                SpUtil.putInt(getApplicationContext(), ConstantValue.TOAST_STYLE, which);
                dialog.dismiss();
                scv_toast_style.setDes(mToastStyleDes[which]);
            }
        });
        //消极按钮
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    /**
     * 是否显示电话号码归属地的方法
     */
    private void initAddress() {
        final SettingItemView siv_address = (SettingItemView) findViewById(R.id.siv_address);
        //对服务是否开的状态做显示
        boolean isRunning = ServiceUtil.isRunning(this, "com.emma.mobilesafe.service.AddressService");
        siv_address.setCheck(isRunning);

        //点击过程中,状态(是否开启电话号码归属地)的切换过程
        siv_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //返回点击前的选中状态
                boolean isCheck = siv_address.isCheck();
                siv_address.setCheck(!isCheck);
                if (!isCheck) {
                    //开启服务,管理吐司
                    startService(new Intent(getApplicationContext(), AddressService.class));
                } else {
                    //关闭服务,不需要显示吐司
                    stopService(new Intent(getApplicationContext(), AddressService.class));
                }
            }
        });
    }

    /**
     * 版本更新开关
     */
    private void initUpdate() {
        final SettingItemView siv_update = (SettingItemView) findViewById(R.id.siv_update);

        //获取已有的开关状态,用作显示
        boolean open_update = SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false);
        //是否选中,根据上一次存储的结果去做决定
        siv_update.setCheck(open_update);

        siv_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果之前是选中的,点击过后,变成未选中
                //如果之前是未选中的,点击过后,变成选中

                //获取之前的选中状态
                boolean isCheck = siv_update.isCheck();
                //将原有状态取反,等同上诉的两部操作
                siv_update.setCheck(!isCheck);
                //将取反后的状态存储到相应sp中
                SpUtil.putBoolean(getApplicationContext(), ConstantValue.OPEN_UPDATE, !isCheck);
            }
        });
    }

}
