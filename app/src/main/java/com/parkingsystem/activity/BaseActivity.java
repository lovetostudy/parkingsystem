package com.parkingsystem.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.Constant;
import com.parkingsystem.utils.HttpPostTask;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

public class BaseActivity extends AppCompatActivity {

    private static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
    }

    public void sendHttpPostRequest(String url, CommonRequest request,
                                     ResponseHandler responseHandler, boolean showLoadingDialog) {
        new HttpPostTask(request, mHandler, responseHandler).execute(url);
        if (showLoadingDialog) {

        }
    }

    protected Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (msg.what == Constant.HANDLER_HTTP_SEND_FAIL) {
                ToastUtils.show(mContext, "请求发送失败请重试");
            } else if (msg.what == Constant.HANDLER_HTTP_RECIVE_FAIL) {
                ToastUtils.show(mContext, "请求接收失败请重试");
            }
        }
    };
}
