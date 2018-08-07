package com.parkingsystem.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.parkingsystem.R;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;

import static com.parkingsystem.utils.Constant.URL_CONTROLLER_TOPUP;

public class TopupActivity extends BaseActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        mContext = this;

        QueryUtils queryUtils = new QueryUtils(mContext);
        final String userName = queryUtils.queryUserName();

        Button btTopupTest = (Button) findViewById(R.id.bt_topup_test);
        btTopupTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String money = "100";
                /**
                 * 充值前需进行数值判定，确保数值大于0
                 */
                final CommonRequest request = new CommonRequest();
                request.addRequestParam("name", userName);
                request.addRequestParam("money", money);

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
        });
    }
}
