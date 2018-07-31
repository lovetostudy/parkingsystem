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
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import org.w3c.dom.Text;

import static com.parkingsystem.utils.Constant.URL_REGISTER;

public class RegisterActivity extends BaseActivity {

    private Context mContext;

    private TextView user_name_ed_register;
    private TextView user_password_ed_register;
    private TextView user_password_ed_re_register;
    private TextView user_mail_register_ed;
    private TextView use_phone_ed;
    private Button register_button;

    private ProgressDialog progressDialog;

//    private Button bt_register_back;
//    private Button bt_register_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = this;

//        bt_register_back = (Button) findViewById(R.id.bt_register_cancel);
//        bt_register_confirm = (Button) findViewById(R.id.bt_register_confirm);

        user_name_ed_register = (TextView) findViewById(R.id.user_name_ed_register);
        user_password_ed_register = (TextView) findViewById(R.id.user_password_ed_register);
        user_password_ed_re_register = (TextView) findViewById(R.id.user_password_ed_re_register);
        user_mail_register_ed = (TextView) findViewById(R.id.user_mail_register_ed);
        use_phone_ed = (TextView) findViewById(R.id.use_phone_ed);
        register_button = (Button) findViewById(R.id.register_button_register);


        setEditListener();
        setButtonListenr();
    }

    /**
     * 获取用户输入的注册信息
     */
    private void setEditListener() {
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = user_name_ed_register.getText().toString();
                String password = user_password_ed_register.getText().toString();
                String re_password = user_password_ed_re_register.getText().toString();
                String mail = user_mail_register_ed.getText().toString();
                String phone = use_phone_ed.getText().toString();

                if (!"".equals(username) && !"".equals(password) && !"".equals(re_password)
                        && !"".equals(mail) && !"".equals(phone)) {
                    if (!re_password.equals(password)) {
                        ToastUtils.show(mContext, "两次输入的密码不一致,请重新输入");
                        user_password_ed_register.setText("");
                        user_password_ed_re_register.setText("");
                    } else if (username.length() < 3) {
                        ToastUtils.show(mContext, "用户名长度不能小于3位,请重新输入");
                    } else if (password.length() < 6) {
                        ToastUtils.show(mContext, "密码长度不能小于6位,请重新输入");
                    } else if (!checkEmail(mail)) {
                        ToastUtils.show(mContext, "邮箱格式不正确,请重新输入");
                    } else if (phone.length() != 11) {
                        ToastUtils.show(mContext, "手机号码格式不正确,请重新输入");
                    } else {
                        register(username, password, phone, mail);
                    }
                } else {
                    ToastUtils.show(mContext, "请完善注册信息!");
                }
            }
        });
    }

    /**
     * 注册用户到服务器
     *
     *
     * @param username
     * @param password
     * @param phone
     * @param mail
     */
    private void register(final String username, final String password, String phone, String mail) {

        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", username);
        request.addRequestParam("password", password);
        request.addRequestParam("email", mail);
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
     * 检查邮箱格式是否正确
     * @param mail
     * @return 正确返回 true, 否则返回 false
     */
    private boolean checkEmail(String mail) {
        // 验证邮箱的正则表达式
        String format = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        if (mail.matches(format))
        {
            return true;// 邮箱名合法，返回true
        }
        else
        {
            return false;// 邮箱名不合法，返回false
        }
    }

    /**
     * 设置按钮点击事件
     */
    private void setButtonListenr() {
        /*bt_register_back.setOnClickListener(new View.OnClickListener() {
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
        });*/
    }
}
