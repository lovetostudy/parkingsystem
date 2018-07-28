package com.parkingsystem.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parkingsystem.R;
import com.parkingsystem.utils.ToastUtils;

public class LoginActivity extends AppCompatActivity {

    private Button bt_login;
    private EditText et_login_username;
    private EditText et_login_password;
    private TextInputLayout til_login_password;
    private TextInputLayout til_login_username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setEditListener();

    }

    /**
     * 检查用户名和密码输入格式是否正确
     */
    private void setEditListener() {

        til_login_username = (TextInputLayout) findViewById(R.id.til_login_username);
        til_login_password = (TextInputLayout) findViewById(R.id.til_login_password);

        bt_login = (Button) findViewById(R.id.bt_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_login_username = (EditText) findViewById(R.id.et_login_username);
                et_login_password = (EditText) findViewById(R.id.et_login_password);
                til_login_username.setHint("用户名");
                til_login_password.setHint("密码");
                final String username = et_login_username.getText().toString();
                final String password = et_login_password.getText().toString();

                // 检查用户名
                et_login_username.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() < 4) {
                            til_login_username.setError("用户名不足四位");
                            til_login_username.setEnabled(true);
                        } else {
                            til_login_username.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() < 4) {
                            til_login_username.setError("用户名不足四位");
                            til_login_username.setEnabled(true);
                        } else {
                            til_login_username.setEnabled(false);
                        }
                    }
                });

                // 检查密码
                et_login_password.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.length() < 6) {
                            til_login_username.setError("密码不足六位");
                            til_login_username.setEnabled(true);
                        } else {
                            til_login_username.setEnabled(false);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if (s.length() < 6) {
                            til_login_username.setError("密码不足六位");
                            til_login_username.setEnabled(true);
                        } else {
                            til_login_username.setEnabled(false);
                        }
                    }
                });

                if (!"".equals(username) && !"".equals(password)) {
                    if (username.length() < 4 && password.length() < 6) {
                        ToastUtils.show(LoginActivity.this, "用户名或密码格式不正确");
                    } else {
                        login(username, password);
                    }
                } else {
                    ToastUtils.show(LoginActivity.this, "用户名或密码不能为空");
                }
            }
        });
    }

    private void login(String username, String password) {

        if ("asdfg".equals(username) && "123456".equals(password)) {
            ToastUtils.show(LoginActivity.this, "login success");
        }
    }
}
