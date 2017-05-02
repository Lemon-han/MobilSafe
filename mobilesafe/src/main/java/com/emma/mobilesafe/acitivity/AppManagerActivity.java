package com.emma.mobilesafe.acitivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.db.domain.AppInfo;
import com.emma.mobilesafe.engine.AppInfoProvider;
import com.emma.mobilesafe.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

public class AppManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private List<AppInfo> mAppInfoList;

    private ListView lv_app_list;
    private MyAdapter mAdapter;

    private List<AppInfo> mSystemList;
    private List<AppInfo> mCustomerList;

    private TextView tv_des;

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mAdapter = new MyAdapter();
            lv_app_list.setAdapter(mAdapter);

            if (tv_des != null && mCustomerList != null) {
                tv_des.setText("用户应用(" + mCustomerList.size() + ")");
            }
        }
    };
    private AppInfo mAppInfo;
    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_manager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("软件管理");
        setSupportActionBar(toolbar);

        initTitle();
        initList();
    }

    private void initList() {
        lv_app_list = (ListView) findViewById(R.id.lv_app_list);
        tv_des = (TextView) findViewById(R.id.tv_des);

        lv_app_list.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                //滚动过程中调用方法
                //AbsListView中view就是listView对象
                //firstVisibleItem第一个可见条目索引值
                //visibleItemCount当前一个屏幕的可见条目数
                //总共条目总数
                if (mCustomerList != null && mSystemList != null) {
                    if (firstVisibleItem >= mCustomerList.size() + 1) {
                        //滚动到了系统条目
                        tv_des.setText("系统应用(" + mSystemList.size() + ")");
                    } else {
                        //滚动到了用户应用条目
                        tv_des.setText("用户应用(" + mCustomerList.size() + ")");
                    }
                }

            }
        });

        lv_app_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0 || position == mCustomerList.size() + 1) {
                    return;
                } else {
                    if (position < mCustomerList.size() + 1) {
                        mAppInfo = mCustomerList.get(position - 1);
                    } else {

                        mAppInfo = mSystemList.get(position - mCustomerList.size() - 2);
                    }
                    showPopupWindow(view);
                }
            }
        });
    }

    private void showPopupWindow(View view) {
        View popupView = View.inflate(this, R.layout.popwindow_ayout, null);
        View tv_uninstall = popupView.findViewById(R.id.tv_uninstall);
        View tv_start = popupView.findViewById(R.id.tv_start);
        View tv_share = popupView.findViewById(R.id.tv_share);

        tv_uninstall.setOnClickListener(this);
        tv_start.setOnClickListener(this);
        tv_share.setOnClickListener(this);

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1,
                0, 1,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        scaleAnimation.setDuration(1000);
        alphaAnimation.setFillAfter(true);

        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        mPopupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                true);
        mPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mPopupWindow.showAsDropDown(view, 200, -view.getHeight());

        popupView.startAnimation(animationSet);
    }

    @Override
    protected void onResume() {
        getData();
        super.onResume();
    }

    private void getData() {

        new Thread() {
            public void run() {
                mAppInfoList = AppInfoProvider.getAppInfoList(getApplicationContext());
                mSystemList = new ArrayList<AppInfo>();
                mCustomerList = new ArrayList<AppInfo>();
                for (AppInfo appInfo : mAppInfoList) {
                    if (appInfo.isSystem) {
                        //系统应用
                        mSystemList.add(appInfo);
                    } else {
                        //用户应用
                        mCustomerList.add(appInfo);
                    }
                }
                mHandler.sendEmptyMessage(0);
            }

            ;
        }.start();
    }

    private void initTitle() {
        // 磁盘(内存)大小
        String path = Environment.getDataDirectory().getAbsolutePath();

        // sd卡大小
        String sdPath = Environment.getExternalStorageDirectory().getAbsolutePath();


        String memoryAvailSpace = Formatter.formatFileSize(this, getAvailSpace(path));
        String sdMemoryAvailSpace = Formatter.formatFileSize(this, getAvailSpace(sdPath));

        TextView tv_memory = (TextView) findViewById(R.id.tv_memory);
        TextView tv_sd_memory = (TextView) findViewById(R.id.tv_sd_memory);

        tv_memory.setText("磁盘可用：" + memoryAvailSpace);
        tv_sd_memory.setText("sd卡可用：" + sdMemoryAvailSpace);

    }


    /**
     * 返回值结果为byte,byte = 8bit,最大结果2147483647bytes
     *
     * @param path
     * @return
     */
    private long getAvailSpace(String path) {
        // 获取可用磁盘大小类
        StatFs statFs = new StatFs(path);
        // 区块的个数、大小
        long count = statFs.getAvailableBlocks();
        long size = statFs.getBlockSize();

        return count * size;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_uninstall:
                if (mAppInfo.isSystem) {
                    ToastUtil.show(getApplicationContext(), "此应用不能卸载");
                } else {
                    Intent intent = new Intent("android.intent.action.DELETE");
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse("package:" + mAppInfo.getPackageName()));
                    startActivity(intent);
                }
                break;

            case R.id.tv_start:
                PackageManager pm = getPackageManager();
                Intent launchIntentForPackage = pm.getLaunchIntentForPackage(mAppInfo.getPackageName());
                if (launchIntentForPackage != null) {
                    startActivity(launchIntentForPackage);
                } else {
                    ToastUtil.show(getApplicationContext(), "此应用不能被开启");
                }
                break;

            case R.id.tv_share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT, "分享一个应用,应用名称为" + mAppInfo.getName());
                intent.setType("text/plain");
                startActivity(intent);
                break;
        }

        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }
    }

    class MyAdapter extends BaseAdapter {

        //获取数据适配器中条目类型的总数,修改成两种(纯文本,图片+文字)
        @Override
        public int getViewTypeCount() {
            return super.getViewTypeCount() + 1;
        }

        //指定索引指向的条目类型,条目类型状态码指定(0(复用系统),1)
        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == mCustomerList.size() + 1) {
                //返回0,代表纯文本条目的状态码
                return 0;
            } else {
                //返回1,代表图片+文本条目状态码
                return 1;
            }
        }

        //listView中添加两个描述条目
        @Override
        public int getCount() {
            return mCustomerList.size() + mSystemList.size() + 2;
        }

        @Override
        public AppInfo getItem(int position) {
            if (position == 0 || position == mCustomerList.size() + 1) {
                return null;
            } else {
                if (position < mCustomerList.size() + 1) {
                    return mCustomerList.get(position - 1);
                } else {
                    //返回系统应用对应条目的对象
                    return mSystemList.get(position - mCustomerList.size() - 2);
                }
            }
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            int type = getItemViewType(position);

            if (type == 0) {
                //展示灰色纯文本条目
                ViewTitleHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(getApplicationContext(), R.layout.listview_app_item_title, null);
                    holder = new ViewTitleHolder();
                    holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewTitleHolder) convertView.getTag();
                }
                if (position == 0) {
                    holder.tv_title.setText("用户应用(" + mCustomerList.size() + ")");
                } else {
                    holder.tv_title.setText("系统应用(" + mSystemList.size() + ")");
                }
                return convertView;
            } else {
                //展示图片+文字条目
                ViewHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(getApplicationContext(), R.layout.listview_app_item, null);
                    holder = new ViewHolder();
                    holder.iv_icon = (ImageView) convertView.findViewById(R.id.iv_icon);
                    holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
                    holder.tv_path = (TextView) convertView.findViewById(R.id.tv_path);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.iv_icon.setBackgroundDrawable(getItem(position).icon);
                holder.tv_name.setText(getItem(position).name);
                if (getItem(position).isSdCard) {
                    holder.tv_path.setText("sd卡应用");
                } else {
                    holder.tv_path.setText("手机应用");
                }
                return convertView;
            }
        }
    }

    static class ViewHolder {
        ImageView iv_icon;
        TextView tv_name;
        TextView tv_path;
    }

    static class ViewTitleHolder {
        TextView tv_title;
    }

}
