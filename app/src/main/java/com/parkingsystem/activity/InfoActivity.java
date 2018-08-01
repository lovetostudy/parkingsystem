package com.parkingsystem.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.User;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import static com.parkingsystem.utils.Constant.OK;
import static com.parkingsystem.utils.Constant.URL_USER_INFO;

public class InfoActivity extends BaseActivity {

    private Context mContext;

    private User user;

    private TextView tv_info_user_name;
    private TextView tv_info_real_name;
    private TextView tv_info_gender;
    private TextView tv_info_car_card;
    private TextView tv_info_balance;
    private TextView tv_info_phone;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == OK) {
                tv_info_user_name.setText(msg.getData().getString("name"));
                tv_info_real_name.setText(msg.getData().getString("real_name"));
                tv_info_gender.setText(msg.getData().getString("gender"));
                tv_info_car_card.setText(msg.getData().getString("car_card"));
                tv_info_balance.setText(msg.getData().getString("balance"));
                tv_info_phone.setText(msg.getData().getString("phone"));
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;

        tv_info_user_name = (TextView) findViewById(R.id.tv_info_user_name);
        tv_info_real_name = (TextView) findViewById(R.id.tv_info_real_name);
        tv_info_gender = (TextView) findViewById(R.id.tv_info_gender);
        tv_info_car_card = (TextView) findViewById(R.id.tv_info_car_card);
        tv_info_balance = (TextView) findViewById(R.id.tv_info_balance);
        tv_info_phone = (TextView) findViewById(R.id.tv_info_phone);

        queryData();
        displayData();
    }

    /**
     * 查询个人信息
     */
    private void queryData() {
        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", "刘一");

        sendHttpPostRequest(URL_USER_INFO, request, new ResponseHandler() {
            @Override
            public void success(final CommonResponse response) {
                if (response != null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain();
                            Bundle bundle = new Bundle();

                            bundle.putString("name", response.getDataList().get(0).get("name"));
                            bundle.putString("real_name", response.getDataList().get(0).get("real_name"));
                            bundle.putString("gender", response.getDataList().get(0).get("gender"));
                            bundle.putString("car_card", response.getDataList().get(0).get("car_card"));
                            bundle.putString("balance", response.getDataList().get(0).get("balance"));
                            bundle.putString("phone", response.getDataList().get(0).get("phone"));
                            msg.setData(bundle);
                            msg.what = OK;

                            if (!"".equals((response.getDataList().get(0).get("name"))) &&
                                    !"".equals((response.getDataList().get(0).get("real_name"))) &&
                                    !"".equals((response.getDataList().get(0).get("gender"))) &&
                                    !"".equals((response.getDataList().get(0).get("car_card"))) &&
                                    !"".equals((response.getDataList().get(0).get("balance"))) &&
                                    !"".equals((response.getDataList().get(0).get("phone")))) {
                                mHandler.sendMessage(msg);
                            }
                        }
                    }).start();
                }
            }

            @Override
            public void error1(CommonResponse response) {

            }

            @Override
            public void error2(CommonResponse response) {

            }

            @Override
            public void fail(String failCode, String failMsg) {
                ToastUtils.show(mContext, "查询失败");
            }
        }, false);
    }

    private void displayData() {

    }
}
