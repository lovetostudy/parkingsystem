package com.parkingsystem.activity;

import android.content.Context;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parkingsystem.R;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import static com.parkingsystem.utils.Constant.OK;
import static com.parkingsystem.utils.Constant.URL_MINE_PARKING_RECORD;
import static com.parkingsystem.utils.Constant.URL_USER_INFO;

public class ParkingRecordActivity extends BaseActivity {

    private Context mContext;

    private String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_record);

        queryData();
    }

    private void queryData() {
        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", "刘一");

        sendHttpPostRequest(URL_MINE_PARKING_RECORD, request, new ResponseHandler() {
            @Override
            public void success(final CommonResponse response) {
                if (response != null) {

                    userName = response.getDataList().get(0).get("user_name");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain();
                            Bundle bundle = new Bundle();

                            ToastUtils.show(mContext, "查询成功");
                            /*bundle.putString("user_name", response.getDataList().get(0).get("user_name"));
                            bundle.putString("real_name", response.getDataList().get(0).get("user_realname"));
                            bundle.putString("gender", response.getDataList().get(0).get("user_gender"));
                            bundle.putString("car_card", response.getDataList().get(0).get("user_card"));
                            bundle.putString("balance", response.getDataList().get(0).get("user_balance"));
                            bundle.putString("phone", response.getDataList().get(0).get("user_phone"));
                            msg.setData(bundle);
                            msg.what = OK;

                            if (!"".equals((response.getDataList().get(0).get("name"))) &&
                                    !"".equals((response.getDataList().get(0).get("real_name"))) &&
                                    !"".equals((response.getDataList().get(0).get("gender"))) &&
                                    !"".equals((response.getDataList().get(0).get("car_card"))) &&
                                    !"".equals((response.getDataList().get(0).get("balance"))) &&
                                    !"".equals((response.getDataList().get(0).get("phone")))) {
                                mHandler.sendMessage(msg);
                            }*/
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

}
