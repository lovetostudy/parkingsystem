package com.parkingsystem.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.parkingsystem.R;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.OnPasswordInputFinish;
import com.parkingsystem.utils.PassView;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import static com.parkingsystem.utils.Constant.URL_CONTROLLER_TOPUP;

public class PayActivity extends BaseActivity implements OnPasswordInputFinish {

    private PassView passView;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        passView = new PassView(this);
        setContentView(passView);
        mContext = this;

        passView.setOnFinishInput(this);
    }
    @Override
    public void inputFinish() {
        QueryUtils queryUtils = new QueryUtils(mContext);
        final String userName = queryUtils.queryUserName();
        String password = queryUtils.queryPassword(userName);

        if (!password.equals(passView.getStrPassword())) {
            ToastUtils.show(mContext, "请输入正确的密码");
        } else {
            final CommonRequest request = new CommonRequest();
            Intent intent = getIntent();
            String price = intent.getStringExtra("price");
            request.addRequestParam("name", userName);
            request.addRequestParam("money", price);

            sendHttpPostRequest(URL_CONTROLLER_TOPUP, request, new ResponseHandler() {
                @Override
                public void success(CommonResponse response) {
                    if (response != null) {
                        final AlertDialog topupDialog = new AlertDialog.Builder(mContext).create();
                        topupDialog.setTitle(response.getResMsg());
                        topupDialog.setMessage("您已成功充值：" + response.getDataList().get(0).get("money") + "元");
                        topupDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        topupDialog.dismiss();
                                        finish();
                                    }
                                });
                        topupDialog.show();
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

                }
            }, false);
        }
    }
}
