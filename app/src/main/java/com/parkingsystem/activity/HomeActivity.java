package com.parkingsystem.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TabHost;

import com.parkingsystem.R;
import com.parkingsystem.fragment.ControllerFragment;
import com.parkingsystem.fragment.LocationFragment;
import com.parkingsystem.fragment.MineFragment;
import com.parkingsystem.utils.TabItem;
import com.parkingsystem.utils.ToastUtils;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity {

    private List<TabItem> mFragmentList;

    private FragmentTabHost mFragmentTabHost;

    private long mExitTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        } else if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS},1);
        } else if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
        } */

        initTabItemData();
    }

    /**
     * 初始化Tab数据
     */
    private void initTabItemData() {
        mFragmentList = new ArrayList<>();
        mFragmentList.add(new TabItem(
                R.drawable.tab_location_normal,
                R.drawable.tab_location_selected,
                "车位信息",
                LocationFragment.class
        ));

        mFragmentList.add(new TabItem(
                R.drawable.tab_control_normal,
                R.drawable.tab_control_selected,
                "开关控制",
                ControllerFragment.class
        ));

        mFragmentList.add(new TabItem(
                R.drawable.tab_mine_normal,
                R.drawable.tab_mine_selected,
                "个人信息",
                MineFragment.class
        ));

        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        // 绑定 FragmentManager
        mFragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i < mFragmentList.size(); i++) {
            TabItem tabItem = mFragmentList.get(i);
            // 创建 tab
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(
                    tabItem.getTabText())
                    .setIndicator(tabItem.getmTabView(HomeActivity.this));
            // 将创建的 tab 添加到底部 tab 栏中 (@android:id/tabs)
            // 将 Fragment 添加到页面中 (@android:id/tabcontent)
            mFragmentTabHost.addTab(tabSpec, tabItem.getFragmentClass(), null);
            // 底部 tab 栏设置北京图片
            mFragmentTabHost.getTabWidget().setBackgroundResource(R.drawable.bottom_bar);

            // 默认选中第一个 tab
            if (i == 0) {
                tabItem.setChecked(true);
            } else {
                tabItem.setChecked(false);
            }
        }

        // 切换 tab 时, 回调此方法
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                for (int i = 0; i < mFragmentList.size(); i++) {
                    TabItem tabItem = mFragmentList.get(i);
                    // 通过 tag 检查用户点击的是哪个 tab
                    if (tabId.equals(tabItem.getTabText())) {
                        tabItem.setChecked(true);
                    } else {
                        tabItem.setChecked(false);
                    }
                }
            }
        });
    }

    /**
     * 设置两次点击返回键退出程序
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN
                && event.getRepeatCount() == 0) {
            if (System.currentTimeMillis() - mExitTime > 2000) {
                mExitTime = System.currentTimeMillis();
                ToastUtils.show(HomeActivity.this, getString(R.string.exit_message));
            } else {
                finish();
            }
        }

        return true;
    }
}
