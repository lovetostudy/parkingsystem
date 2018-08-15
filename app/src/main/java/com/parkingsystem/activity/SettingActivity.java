package com.parkingsystem.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parkingsystem.R;
import com.parkingsystem.adapter.SettingAdapter;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity {

    private Context mContext;

    private final int CHECK_UPDATE = 0;
    private final int ABOUT_US = 1;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CHECK_UPDATE:
                    checkUpdate();
                    break;
                case ABOUT_US:
                    aboutUs();
                    break;
            }
        }
    };

    /**
     * 关于我们的信息
     */
    private void aboutUs() {
        final AlertDialog leaveDialog = new AlertDialog.Builder(mContext).create();
        leaveDialog.setTitle("制作团队: ");
        leaveDialog.setMessage("杨帆启航: 吴飞清 张凯 周雄");
        leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        leaveDialog.dismiss();
                    }
                });
        leaveDialog.show();
    }


    /**
     * 检查更新
     */
    private void checkUpdate() {
        final AlertDialog leaveDialog = new AlertDialog.Builder(mContext).create();
        leaveDialog.setTitle("更新提醒: ");
        leaveDialog.setMessage("您当前应用已是最新版本");
        leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        leaveDialog.dismiss();
                    }
                });
        leaveDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mContext = this;

        final ArrayList<String> setting = new ArrayList<>();
        setting.add("检查更新");
        setting.add("关于我们");
        setting.add("");

        SettingAdapter settingAdapter = new SettingAdapter(mContext, setting);
        ListView settingTv = (ListView) findViewById(R.id.setting_lv);
        settingTv.setAdapter(settingAdapter);

        settingTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                final String settingClick = setting.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        switch (settingClick) {
                            case "检查更新":
                                msg.what = CHECK_UPDATE;
                                break;
                            case "关于我们":
                                msg.what = ABOUT_US;
                                break;
                            default:
                        }
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }
}
