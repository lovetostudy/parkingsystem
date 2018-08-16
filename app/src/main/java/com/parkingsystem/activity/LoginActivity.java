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
import com.parkingsystem.entity.User;
import com.parkingsystem.logs.LogUtil;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

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

        setLoginListener();
        setReturnListener();
        setRigsterListener();
    }

    /**
     * 检查用户名和密码输入格式是否正确
     */
    private void setLoginListener() {

        bt_login = (Button) findViewById(R.id.bt_login_login);
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_login_username = (EditText) findViewById(R.id.et_login_username);
                et_login_password = (EditText) findViewById(R.id.et_login_password);

                final String username = et_login_username.getText().toString();
                final String password = et_login_password.getText().toString();


                if (!"".equals(username) && !"".equals(password)) {
                    if (username.length() < 1) {
                        ToastUtils.show(mContext, "用户名格式不正确,");
                    } else if (password.length() < 6) {
                        ToastUtils.show(mContext, "密码格式不正确");
                    } else {
                        login(username, password);
                    }
                } else {
                    ToastUtils.show(mContext, "用户名或密码不能为空");
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
    private void setRigsterListener() {
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
     *
     * @param username
     * @param password
     */
    private void login(final String username, String password) {
        final QueryUtils queryUtils = new QueryUtils(mContext);
        final CommonRequest request = new CommonRequest();

        request.addRequestParam("name", username);
        request.addRequestParam("password", password);
        progressDialog = ProgressDialog.show(mContext, "请稍后",
                "正在登录...");
        sendHttpPostRequest(URL_LOGIN, request, new ResponseHandler() {
            public void success(CommonResponse response) {
                if (response != null) {
                    User user = new User();
                    user.username = response.getDataList().get(0).get("user_name");
                    user.password = response.getDataList().get(0).get("user_password");
                    user.realname = response.getDataList().get(0).get("user_realname");
                    user.gender = response.getDataList().get(0).get("user_gender");
                    user.card = response.getDataList().get(0).get("user_card");
                    user.phone = response.getDataList().get(0).get("user_phone");
                    user.balance = response.getDataList().get(0).get("user_balance");
                    user.carState = response.getDataList().get(0).get("user_card_state");

                    queryUtils.addUserInfo(user);
                }
                progressDialog.dismiss();

                String name = queryUtils.queryUserName();

                ToastUtils.show(mContext, "登录成功! " + name + ",欢迎你回来!");

                finish();
            }

            public void error1(CommonResponse response) {
                progressDialog.dismiss();
                ToastUtils.show(mContext, "登录失败,账户或密码错误!");
            }

            public void error2(CommonResponse response) {
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
