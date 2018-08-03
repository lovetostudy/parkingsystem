package com.parkingsystem.activity;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.entity.User;
import com.parkingsystem.entity.UserInfo;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.MineAdapter;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;
import com.parkingsystem.utils.UserInfoAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.parkingsystem.utils.Constant.OK;
import static com.parkingsystem.utils.Constant.URL_USER_INFO;

public class InfoActivity extends BaseActivity {

    private static int CREATED = 0;

    private Context mContext;

    private User user;

    private List<UserInfo> userInfoList = new ArrayList<>();

    private HashMap<String, String> infoMap = new HashMap<>();

    private String userName;

    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;

        UserInfoAdapter adapter = new UserInfoAdapter(mContext, R.layout.info_item, userInfoList);
        ListView listView = (ListView) findViewById(R.id.info_list_view);
        listView.setAdapter(adapter);

        queryData();
        initInfo();
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

                    userName = response.getDataList().get(0).get("user_name");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain();
                            Bundle bundle = new Bundle();

                            infoMap.put("user_name", response.getDataList().get(0).get("user_name"));

                            bundle.putString("user_name", response.getDataList().get(0).get("user_name"));
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



    /**
     * 初始化个人信息界面
     */
    private void initInfo() {
        if (CREATED == 0) {
            /*ArrayList<String> keyList = infoMap.keySet();*/
            /*for (int i = 0; i < ) {*/
                UserInfo userNameInfo = new UserInfo("用户名", "userName");
                userInfoList.add(userNameInfo);


                /*mHandler = mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if (msg.what == OK) {

                            msg.getData().getString("name");
                            msg.getData().getString("real_name");
                            msg.getData().getString("gender");
                            msg.getData().getString("car_card");
                            msg.getData().getString("balance");
                            msg.getData().getString("phone");
                        }
                    }
                }*/
            }

            CREATED = 1;
        /*}*/
    }
}
