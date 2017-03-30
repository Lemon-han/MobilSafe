package com.emma.mobilesafe.acitivity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.adapter.MyHomeAdapter;
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

    private void initUI() {
        rv_home = (RecyclerView) findViewById(R.id.rv_home);
        final GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        rv_home.setLayoutManager(layoutManager);


    }


}
