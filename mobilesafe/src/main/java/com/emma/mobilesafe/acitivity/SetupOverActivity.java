package com.emma.mobilesafe.acitivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

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


            initUI();


        } else {
            Intent intent = new Intent(this, Setup1Activity.class);
            startActivity(intent);

            finish();
        }


    }

    private void initUI() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv_phone = (TextView) findViewById(R.id.tv_phone);
        tv_phone.setText(SpUtil.getString(this, ConstantValue.CONTACT_PHONE, ""));


        TextView tv_reset_setup = (TextView) findViewById(R.id.tv_reset_setup);
        tv_reset_setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setup1Activity.class);
                startActivity(intent);

                finish();
            }
        });

    }

}
