package com.emma.mobilesafe.acitivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.SpUtil;
import com.emma.mobilesafe.utils.StringUtil;
import com.emma.mobilesafe.utils.ToastUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class SplashActivity extends AppCompatActivity {

    private static final String TAG = "SplashActivity";

    protected static final int UPDATE_VERSION = 100;
    protected static final int ENTER_HOME = 101;
    protected static final int URL_ERROR = 102;
    protected static final int IO_ERROR = 103;
    protected static final int JSON_ERROR = 104;

    private TextView tv_version_name;
    private RelativeLayout rl_root;
    private int mLocalVersionCode;

    private String mVersionDes;
    private String mDownloadUrl;
    private String mVersionCode;
    private String mVersionName;


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_VERSION:
                    showUpdateDialog();
                    break;

                case ENTER_HOME:
                    enterHome();
                    break;

                case URL_ERROR:
                    ToastUtil.show(getApplicationContext(), "url异常");
                    enterHome();
                    break;

                case IO_ERROR:
                    ToastUtil.show(getApplicationContext(), "io异常");
                    enterHome();
                    break;

                case JSON_ERROR:
                    ToastUtil.show(getApplicationContext(), "json异常");
                    enterHome();
                    break;
            }
        }

        ;
    };


    protected void enterHome() {
        Intent intent = new Intent(this, HomeAcitivity.class);
        startActivity(intent);

        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        initUI();

        initData();

        initAnimation();

        initDB();

        if (!SpUtil.getBoolean(this, ConstantValue.HAS_SHORTCUT, false)) {
            //生成快捷方式
            initShortCut();
        }

    }

    /**
     * 生成快捷方式
     */
    private void initShortCut() {
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, android.graphics.BitmapFactory.decodeResource(getResources(), R.mipmap.icon));
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "啊哈哈");

        Intent shortCutIntent = new Intent("com.emma.mobilesafe.HOME");
        shortCutIntent.addCategory("android.intent.category.DEFAULT");
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortCutIntent);

        sendBroadcast(intent);

        SpUtil.putBoolean(this, ConstantValue.HAS_SHORTCUT, true);
    }

    private void initDB() {
        initAddressDB("address.db");
        initAddressDB("commonnum.db");
        initAddressDB("antivirus.db");
    }

    /**
     * 读取数据库files文件夹下
     *
     * @param dbName
     */
    private void initAddressDB(String dbName) {
        File files = getFilesDir();
        File file = new File(files, dbName);
        if (file.exists()) {
            return;
        }
        InputStream stream = null;
        FileOutputStream fos = null;

        try {
            stream = getAssets().open(dbName);
            fos = new FileOutputStream(file);

            byte[] bs = new byte[1024];
            int tmp = -1;
            while ((tmp = stream.read(bs)) != -1) {
                fos.write(bs, 0, tmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stream != null && fos != null) {
                try {
                    stream.close();
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(3000);
        rl_root.startAnimation(alphaAnimation);
    }

    /**
     * 弹出对话框提示用户更新
     */
    protected void showUpdateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.icon);
        builder.setTitle("版本更新");
        builder.setMessage(mVersionDes);
        builder.setPositiveButton("立即更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                downloadApk();
            }
        });
        builder.setNegativeButton("稍后再说", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                enterHome();
            }
        });

        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                enterHome();
                dialog.dismiss();
            }
        });
        builder.show();

    }

    protected void downloadApk() {

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory().getAbsoluteFile()
                    + File.separator + "mobilesafe-debug.apk";

            HttpUtils httpUtils = new HttpUtils();
            Log.i(TAG, "download success" + path);
            httpUtils.download(mDownloadUrl, path, new RequestCallBack<File>() {
                @Override
                public void onSuccess(ResponseInfo<File> info) {
                    Log.i(TAG, "download success");
                    File file = info.result;
                    installApk(file);
                }

                @Override
                public void onFailure(HttpException e, String s) {

                }

                @Override
                public void onStart() {
                    super.onStart();
                }

                @Override
                public void onLoading(long total, long current, boolean isUploading) {
                    super.onLoading(total, current, isUploading);
                    Log.i(TAG, "pending");
                    Log.i(TAG, "pending" + current + "//" + total);
                    Log.i(TAG, "pending");
                }
            });
        }
    }

    /**
     * 安装对应apk
     */
    protected void installApk(File file) {
        //系统界面
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        enterHome();
        super.onActivityResult(requestCode, resultCode, data);

    }

    /**
     * data
     */
    private void initData() {
        tv_version_name.setText("版本名称" + getVersionName());
        mLocalVersionCode = getVersionCode();

        if (SpUtil.getBoolean(this, ConstantValue.OPEN_UPDATE, false)) {
            checkVersion();
        } else {
            mHandler.sendEmptyMessageDelayed(ENTER_HOME, 3000);
        }

    }

    /**
     * 检查版本号
     */
    private void checkVersion() {
        new Thread() {


            @Override
            public void run() {
                Message msg = Message.obtain();
                long startTime = System.currentTimeMillis();
                try {
                    URL url = new URL("http://10.205.1.46:8080/update.json");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(2000);
                    connection.setReadTimeout(2000);
                    connection.setRequestMethod("GET");
                    if (connection.getResponseCode() == 200) {
                        InputStream is = connection.getInputStream();
                        String json = StringUtil.streamToString(is);

                        Log.i(TAG, "run: " + json);
                        JSONObject jsonObject = new JSONObject(json);
                        mVersionName = jsonObject.getString("versionName");
                        mVersionDes = jsonObject.getString("versionDes");
                        mVersionCode = jsonObject.getString("versionCode");
                        mDownloadUrl = jsonObject.getString("downloadUrl");

                        if (mLocalVersionCode < Integer.parseInt(mVersionCode)) {
                            msg.what = UPDATE_VERSION;
                        } else {
                            msg.what = ENTER_HOME;
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    msg.what = URL_ERROR;
                } catch (IOException e) {
                    e.printStackTrace();
                    msg.what = IO_ERROR;
                } catch (JSONException e) {
                    e.printStackTrace();
                    msg.what = JSON_ERROR;
                } finally {
                    long endTime = System.currentTimeMillis();
                    if (endTime - startTime < 2000) {
                        try {
                            Thread.sleep(2000 - (endTime - startTime));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mHandler.sendMessage(msg);
                }
            }
        }.start();
    }

    /**
     * 初始化UI
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    /**
     * 获取版本名称
     *
     * @return 应用版本名称
     */
    public String getVersionName() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回版本号
     *
     * @return
     */
    public int getVersionCode() {
        PackageManager pm = getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
