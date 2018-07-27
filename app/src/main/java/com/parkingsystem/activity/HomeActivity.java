package com.parkingsystem.activity;

import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

import com.parkingsystem.R;
import com.parkingsystem.fragment.ControllerFragment;
import com.parkingsystem.fragment.LocationFragment;
import com.parkingsystem.fragment.MineFragment;
import com.parkingsystem.utils.TabItem;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private List<TabItem> mFragmentList;

    private FragmentTabHost mFragmentTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
}
