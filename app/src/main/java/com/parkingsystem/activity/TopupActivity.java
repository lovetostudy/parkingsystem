package com.parkingsystem.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.adapter.TopupAdapter;
import com.parkingsystem.entity.TopupItem;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;

import java.util.ArrayList;

import static com.parkingsystem.utils.Constant.URL_CONTROLLER_TOPUP;

public class TopupActivity extends BaseActivity {

    private Context mContext;
    private Button btTopupTest;
    private String userName;

    private int[] ints = {10, 20, 50, 100, 200, 300, 400, 500};
    private EditText etTopupMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topup);
        mContext = this;

        QueryUtils queryUtils = new QueryUtils(mContext);
        userName = queryUtils.queryUserName();
        TextView textView = (TextView) findViewById(R.id.topup_price_name);

        /*RecyclerView recyclerView = (RecyclerView) findViewById(R.id.price_label);
        TopupAdapter adapter = new TopupAdapter(mContext, getData());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(adapter);*/
        /*adapter.replaceAll(getData());*/
        etTopupMoney = (EditText) findViewById(R.id.et_topup_money);

        btTopupTest = (Button) findViewById(R.id.bt_topup_test);
        topupButtonListener();
    }

    /**
     * 设置 recyclerView 填充的数据
     */
    private ArrayList<TopupItem> getData() {
        ArrayList<TopupItem> list = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            String price = ints[i] + "元";

            list.add(new TopupItem(TopupItem.TEXT_TYPE, price));
        }
        /*list.add(new TopupItem(TopupItem.EDIT_TYPE, null));
*/
        return list;
    }

    /**
     * 设置充值点击事件
     */
    private void topupButtonListener() {
        btTopupTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String money;
                money = etTopupMoney.getText().toString();
                /**
                 * 充值前需进行数值判定，确保数值大于0
                 */
                final CommonRequest request = new CommonRequest();
                request.addRequestParam("name", userName);
                request.addRequestParam("money", money);

                final AlertDialog topupDialog = new AlertDialog.Builder(mContext).create();
                topupDialog.setTitle("充值提醒: ");
                topupDialog.setMessage("是否充值: " + money + "元");
                topupDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                topupDialog.dismiss();

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
                topupDialog.show();



            }
        });
    }
}
