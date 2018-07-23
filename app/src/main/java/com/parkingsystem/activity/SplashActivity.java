package com.parkingsystem.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingsystem.R;
import com.parkingsystem.utils.ToastUtils;

public class SplashActivity extends AppCompatActivity {

    private TextView tv_version_name;

    private RelativeLayout rl_root;

    protected static final int ENTER_HOME = 100;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == ENTER_HOME) {
                enterHome();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //初始化UI
        initUI();
        //初始化数据
        initData();
        //初始化动画
        initAnimation();
    }


    /**
     * 初始化UI
     */
    private void initUI() {
        tv_version_name = (TextView) findViewById(R.id.tv_version_name);
        rl_root = (RelativeLayout) findViewById(R.id.rl_root);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        tv_version_name.setText("版本名称:" + getVersionName());
        //让加载界面显示3秒
        new Thread() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = ENTER_HOME;
                try {
                    Thread.sleep(3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mHandler.sendMessage(msg);
            }
        }.start();
    }


    /**
     * 获取版本名称
     *
     * @return 版本名称, 否则返回null
     */
    public String getVersionName() {
        PackageManager packageManager = getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(getPackageName(), 0);
            return packageInfo.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 进入主界面
     */
    private void enterHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        //开启主界面后,关闭导航界面
        finish();
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        alphaAnimation.setDuration(1000);
        rl_root.startAnimation(alphaAnimation);
    }
}
