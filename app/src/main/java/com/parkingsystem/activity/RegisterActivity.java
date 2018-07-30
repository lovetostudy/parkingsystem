package com.parkingsystem.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parkingsystem.R;
import com.parkingsystem.utils.ToastUtils;

public class RegisterActivity extends BaseActivity {

    private Button bt_register_back;
    private Button bt_register_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        bt_register_back = (Button) findViewById(R.id.bt_register_cancel);
        bt_register_confirm = (Button) findViewById(R.id.bt_register_confirm);


        setEditListener();
        setButtonListenr();
    }

    /**
     * 获取用户输入的注册信息
     */
    private void setEditListener() {

    }

    /**
     * 设置按钮点击事件
     */
    private void setButtonListenr() {
        bt_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_register_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getApplicationContext(), "注册成功");
            }
        });
    }
}
