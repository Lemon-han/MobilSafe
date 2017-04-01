package com.emma.mobilesafe.acitivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.adapter.MyHomeAdapter;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.Md5Util;
import com.emma.mobilesafe.utils.SpUtil;
import com.emma.mobilesafe.utils.ToastUtil;
import com.emma.mobilesafe.view.RecyclerViewClickListener;

/**
 * Created by Administrator on 2017/3/27.
 */
public class HomeAcitivity extends Activity {

    private RecyclerView rv_home;
    private MyHomeAdapter mAdapter;
    String[] mTitleStr;
    int[] mDrawableIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_home);

        initUI();
        initData();
    }

    private void initData() {
        mTitleStr = new String[]{"手机防盗", "通信卫士", "软件管理", "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心"};
        mDrawableIds = new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher, R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,};

        mAdapter = new MyHomeAdapter(mDrawableIds, mTitleStr);


        rv_home.setAdapter(mAdapter);
        rv_home.addOnItemTouchListener(new RecyclerViewClickListener(this, rv_home,
                new RecyclerViewClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (position) {
                            case 0:
                                showDialog();
                                break;
                            case 1:
                                break;
                            case 2:
                                break;
                            case 3:
                                break;
                            case 4:
                                break;
                            case 5:
                                break;
                            case 6:
                                break;
                            case 7:
                                break;
                            case 8:
                                Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                                startActivity(intent);
                                break;
                        }
                    }

                    @Override
                    public void onItemLongClick(View view, int position) {
                        Toast.makeText(HomeAcitivity.this, "Click " + mTitleStr[position], Toast.LENGTH_SHORT).show();
                    }
                }));

    }

    private void showDialog() {
        String psd = SpUtil.getString(this, ConstantValue.MOBILE_SAFE_PSD, "");
        if (TextUtils.isEmpty(psd)) {
            showSetPsdDialog();
        } else {
            showConfirmPsdDialog();
        }
    }

    /**
     * 确认密码对话框
     */
    private void showConfirmPsdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(this, R.layout.dialog_confirm_psd, null);
//        dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_confirm_pwd = (EditText) view.findViewById(R.id.et_confirm_pwd);


                String confirmedPsd = et_confirm_pwd.getText().toString();

                if (!TextUtils.isEmpty(confirmedPsd)) {
                    String psd = SpUtil.getString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, "");

                    if (psd.equals(Md5Util.encoder(confirmedPsd))) {
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);

                        dialog.dismiss();

                        SpUtil.putString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, psd);

                    } else {
                        ToastUtil.show(getApplicationContext(), "密码错误");
                        et_confirm_pwd.setText("");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), "请输入秘密");
                }
            }
        });
        Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    /**
     * 设置密码对话框
     */
    private void showSetPsdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        final View view = View.inflate(this, R.layout.dialog_set_psd, null);
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();

        Button bt_submit = (Button) view.findViewById(R.id.bt_submit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_set_psd = (EditText) view.findViewById(R.id.et_set_pwd);
                EditText et_confirm_pwd = (EditText) view.findViewById(R.id.et_confirm_pwd);


                String psd = et_set_psd.getText().toString();
                String confirmedPsd = et_confirm_pwd.getText().toString();

                if (!TextUtils.isEmpty(psd) && !TextUtils.isEmpty(confirmedPsd)) {
                    //进入手机防盗模块
                    if (psd.equals(confirmedPsd)) {
                        Intent intent = new Intent(getApplicationContext(), SetupOverActivity.class);
                        startActivity(intent);

                        dialog.dismiss();

                        SpUtil.putString(getApplicationContext(), ConstantValue.MOBILE_SAFE_PSD, Md5Util.encoder(psd));

                    } else {
                        ToastUtil.show(getApplicationContext(), "两次密码输入不一致");
                        et_set_psd.setText("");
                        et_confirm_pwd.setText("");
                    }
                } else {
                    ToastUtil.show(getApplicationContext(), "请输入秘密");
                }
            }
        });
        Button bt_cancle = (Button) view.findViewById(R.id.bt_cancle);
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void initUI() {
        rv_home = (RecyclerView) findViewById(R.id.rv_home);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        rv_home.setLayoutManager(layoutManager);
    }


}
