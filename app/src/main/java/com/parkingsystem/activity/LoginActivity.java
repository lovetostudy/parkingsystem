package com.parkingsystem.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import static com.parkingsystem.utils.Constant.URL_LOGIN;

public class LoginActivity extends BaseActivity {

    private Context mContext;

    private Button bt_login;
    private EditText et_login_username;
    private EditText et_login_password;
    private TextInputLayout til_login_password;
    private TextInputLayout til_login_username;
    private Button bt_login_back;
    private TextView tv_rigister;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext = this;

        setEditListener();
        setReturnListener();
        setTextListener();
    }

    /**
     * 检查用户名和密码输入格式是否正确
     */
    private void setEditListener() {

        til_login_username = (TextInputLayout) findViewById(R.id.til_login_username);
        til_login_password = (TextInputLayout) findViewById(R.id.til_login_password);

        bt_login = (Button) findViewById(R.id.bt_login_login);
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

    /**
     * 设置返回按钮 返回主界面 (有bug)
     */
    private void setReturnListener() {
        bt_login_back = (Button) findViewById(R.id.bt__login_back);

        bt_login_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置 textView 的注册点击事件
     */
    private void setTextListener() {
        tv_rigister = (TextView) findViewById(R.id.tv_login_register);
        tv_rigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 登录到服务器
     * @param username
     * @param password
     */
    private void login(final String username, String password) {

        final CommonRequest request = new CommonRequest();

        request.addRequestParam("name", username);
        request.addRequestParam("password", password);
        progressDialog = ProgressDialog.show(mContext, "请稍后...",
                "正在登录");
        sendHttpPostRequest(URL_LOGIN, request, new ResponseHandler() {

            public void success(CommonResponse response) {
                progressDialog.dismiss();
                ToastUtils.show(mContext, "登录成功!" + username);

                finish();
            }

            public void success1(CommonResponse response) {
                progressDialog.dismiss();
                ToastUtils.show(mContext, "登录失败,账户或密码错误!");
            }

            public void success2(CommonResponse response) {
                progressDialog.dismiss();
                ToastUtils.show(mContext, "登录失败,该用户未注册!");
            }

            @Override
            public void fail(String failCode, String failMsg) {
                progressDialog.dismiss();
            }
        }, true);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 8000);
    }
}
