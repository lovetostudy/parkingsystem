package com.parkingsystem.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import org.w3c.dom.Text;

import static com.parkingsystem.utils.Constant.URL_LOGIN;
import static com.parkingsystem.utils.Constant.URL_REGISTER;

public class RegisterActivity extends BaseActivity {

    private Context mContext;

    private ProgressDialog progressDialog;

    private EditText et_register_username;
    private EditText et_register_password;
    private EditText et_register_re_password;
    private EditText et_register_real_name;
    private EditText et_register_car_card;
    private EditText et_register_phone;
    private RadioGroup rg_register_gender;
    private Button bt_register_back;
    private Button bt_register_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;

        et_register_username = (EditText) findViewById(R.id.et_register_user_name);
        et_register_password = (EditText) findViewById(R.id.et_register_password);
        et_register_re_password = (EditText) findViewById(R.id.et_register_re_password);
        et_register_real_name = (EditText) findViewById(R.id.et_register_real_name);
        rg_register_gender = (RadioGroup) findViewById(R.id.rg_register_gender);
        et_register_car_card = (EditText) findViewById(R.id.et_register_car_card);
        et_register_phone = (EditText) findViewById(R.id.et_register_phone);
        bt_register_back = (Button) findViewById(R.id.bt_register_cancel);
        bt_register_confirm = (Button) findViewById(R.id.bt_register_confirm);

        setComfirmButtonListener();
        setBackButtonListenr();
    }

    /**
     * 用户注册确认点击事件
     */
    private void setComfirmButtonListener() {
        bt_register_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_register_username.getText().toString();
                String password = et_register_password.getText().toString();
                String re_password = et_register_re_password.getText().toString();
                String real_name = et_register_real_name.getText().toString();
                String car_card = et_register_car_card.getText().toString();
                String phone = et_register_phone.getText().toString();
                String gender = null;

                for (int i = 0; i < rg_register_gender.getChildCount(); i++) {
                    RadioButton rb_gender = (RadioButton) rg_register_gender.getChildAt(i);
                    if (rb_gender.isChecked()) {
                        gender = (String) rb_gender.getText();
                        break;
                    }
                }

                if (!"".equals(username) && !"".equals(password) && !"".equals(re_password)
                        && !"".equals(real_name) && !"".equals(car_card) && !"".equals(phone)) {
                    if (!re_password.equals(password)) {
                        ToastUtils.show(mContext, "两次输入的密码不一致,请重新输入");
                    } else if (username.length() < 2) {
                        ToastUtils.show(mContext, "用户名长度不能小于2位,请重新输入");
                    } else if (password.length() < 6) {
                        ToastUtils.show(mContext, "密码长度不能小于6位,请重新输入");
                    } else if (phone.length() != 11) {
                        ToastUtils.show(mContext, "手机号码格式不正确,请重新输入");
                    } else {
                        register(username, password, real_name, gender, car_card, phone);
                    }
                } else {
                    ToastUtils.show(mContext, "请完善注册信息!");
                }
            }
        });
    }

    /**
     * 注册用户信息到服务器
     *
     * @param username  用户名
     * @param password  密码
     * @param real_name 真实姓名
     * @param gender    性别
     * @param car_card  车牌号
     * @param phone     手机号
     */
    private void register(final String username, final String password,
                          String real_name, String gender, String car_card, String phone) {

        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", username);
        request.addRequestParam("password", password);
        request.addRequestParam("real_name", real_name);
        request.addRequestParam("gender", gender);
        request.addRequestParam("car_card", car_card);
        request.addRequestParam("phone", phone);

        progressDialog = ProgressDialog.show(mContext, "请稍后", "注册中...");

        sendHttpPostRequest(URL_REGISTER, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                progressDialog.dismiss();
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("提示信息");
                alertDialog.setMessage("注册成功");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确   定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog.show();
            }

            @Override
            public void error1(CommonResponse response) {
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("提示信息");
                alertDialog.setMessage("注册失败,用户已存在");
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确   定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });
                alertDialog.show();
            }

            @Override
            public void error2(CommonResponse response) {

            }

            @Override
            public void fail(String failCode, String failMsg) {
                progressDialog.dismiss();
                final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setTitle("提示信息");
                alertDialog.setMessage("注册失败," + failMsg);
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确   定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                        finish();
                    }
                });
                alertDialog.show();
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

    /**
     * 返回按钮
     */
    private void setBackButtonListenr() {
        bt_register_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
