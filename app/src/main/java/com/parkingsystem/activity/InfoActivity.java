package com.parkingsystem.activity;

import android.content.Context;
import android.content.Intent;
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
import com.parkingsystem.logs.LogUtil;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.MineAdapter;
import com.parkingsystem.utils.QueryUtils;
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

    private ArrayList<UserInfo> userInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        mContext = this;

        initData();

        UserInfoAdapter adapter = new UserInfoAdapter(mContext, userInfoList);
        ListView listView = (ListView) findViewById(R.id.info_list_view);
        listView.setAdapter(adapter);
    }

    /**
     * 将用户信息放入 arrayList 中
     */
    private void initData() {
        QueryUtils queryUtils= new QueryUtils(mContext);
        String userName = queryUtils.queryUserName();
        User user = queryUtils.queryUserInfo(userName);
        UserInfo username = new UserInfo("用户名", user.username);
        userInfoList.add(username);
        UserInfo realname = new UserInfo("真实姓名", user.realname);
        userInfoList.add(realname);
        UserInfo gender = new UserInfo("性  别", user.gender);
        userInfoList.add(gender);
        UserInfo card = new UserInfo("车牌号", user.card);
        userInfoList.add(card);
        UserInfo phone = new UserInfo("手机号", user.phone);
        userInfoList.add(phone);
        UserInfo balance = new UserInfo("余  额", user.balance);
        userInfoList.add(balance);
        UserInfo space = new UserInfo("  ", " ");
        userInfoList.add(space);
    }

    /**
     * 查询个人信息
     */
    /*private void queryData() {
        final QueryUtils queryUtils = new QueryUtils(mContext);
        final CommonRequest request = new CommonRequest();
        userName = queryUtils.queryUserName();
        LogUtil.logArrayListInfo("queryed userName:  ", userName);
        request.addRequestParam("name", "刘一");

        sendHttpPostRequest(URL_USER_INFO, request, new ResponseHandler() {
            @Override
            public void success(final CommonResponse response) {

                *//*infoMap.put("userName", "小刘");*//*
                if (response != null) {

                  *//*  userName = response.getDataList().get(0).get("user_name");*//*
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message msg = Message.obtain();
                            Bundle bundle = new Bundle();

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
*/


    /**
     * 初始化个人信息界面
     */
    /*private void initInfo() {
        if (CREATED == 0) {
            *//*ArrayList<String> keyList = infoMap.keySet();*//*
            *//*for (int i = 0; i < ) {*//*
            LogUtil.logArrayListInfo("initInfoList : infoMap 解析", infoMap.get("userName"));
            if (infoMap.get("userName") == null) {
                UserInfo userNameInfo = new UserInfo("用户名1", "为空");
                LogUtil.logArrayListInfo("initInfoList null : infoMap 解析", infoMap.get("userName"));
                userInfoList.add(userNameInfo);
            } else {
                UserInfo userNameInfo = new UserInfo("用户名1", infoMap.get("userName"));
                userInfoList.add(userNameInfo);
            }
        }

            CREATED = 1;
        *//*}*//*
    }*/
}
