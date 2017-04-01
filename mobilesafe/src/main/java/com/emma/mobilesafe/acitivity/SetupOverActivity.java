package com.emma.mobilesafe.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.emma.mobilesafe.R;
import com.emma.mobilesafe.utils.ConstantValue;
import com.emma.mobilesafe.utils.SpUtil;

public class SetupOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Boolean setup_over = SpUtil.getBoolean(this, ConstantValue.SETUP_OVER, false);
        if (setup_over) {
            //停留在设置完成界面
            setContentView(R.layout.activity_setup_over);

            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);


        } else {
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);

            finish();
        }


    }

}
