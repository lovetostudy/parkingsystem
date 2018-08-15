package com.parkingsystem.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.activity.BaseActivity;
import com.parkingsystem.activity.InfoActivity;
import com.parkingsystem.activity.LoginActivity;
import com.parkingsystem.activity.ParkingRecordActivity;
import com.parkingsystem.activity.SettingActivity;
import com.parkingsystem.activity.TopupRecordActivity;
import com.parkingsystem.entity.Mine;
import com.parkingsystem.entity.ParkingInfo;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.adapter.MineAdapter;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import static com.parkingsystem.utils.Constant.URL_MINE_PARKING_RECORD;
import static com.parkingsystem.utils.Constant.URL_MINE_TOPUP_RECORD;

public class MineFragment extends Fragment {

    private static final int USER_INFO = 1;
    private static final int PARKING_RECORD = 2;
    private static final int TOPUP_RECORD = 3;
    private static final int SETTING = 4;

    private static int CREATED = 0;

    private List<Mine> mineList = new ArrayList<>();

    private String userName;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USER_INFO:
                    enterUserInfo();
                    break;
                case PARKING_RECORD:
                    enterParkingRecord();
                    break;
                case TOPUP_RECORD:
                    enterTopupRecord();
                    break;
                case SETTING:
                    enterSetting();
                    break;
                default:
            }
        }
    };

    private Mine mine;
    private ImageView headIcon;
    private TextView tvLogin;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        MineAdapter adapter = new MineAdapter(getContext(), R.layout.mine_item, mineList);
        ListView listView = (ListView) view.findViewById(R.id.mine_list_view);
        listView.setAdapter(adapter);

        headIcon = (ImageView) view.findViewById(R.id.mine_icon);
        tvLogin = (TextView) view.findViewById(R.id.mine_state);

        initMine();     // 初始化 mineList 数据
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view1, int position, long id) {
                mine = mineList.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = Message.obtain();
                        switch (mine.getName()) {
                            case "个人信息":
                                msg.what = USER_INFO;
                                break;
                            case "停车记录":
                                msg.what = PARKING_RECORD;
                                break;
                            case "充值记录":
                                msg.what = TOPUP_RECORD;
                                break;
                            case "设置":
                                msg.what = SETTING;
                                break;
                            default:
                        }
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });

        headIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterLogin();

            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterLogin();
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        QueryUtils queryUtils = new QueryUtils(getContext());
        String username = queryUtils.queryUserName();
        if (!"".equals(username)) {
            headIcon.setImageDrawable(getResources().getDrawable(R.drawable.after));
            tvLogin.setText(username);
            tvLogin.setTextColor(Color.BLACK);
            headIcon.setEnabled(false);
            tvLogin.setEnabled(false);

            updateParkingRecord(username);
            updateTopupRecord(username);
        }
    }

    /**
     * 初始化 ListView
     */
    private void initMine() {
        if (CREATED == 0) {     // 判断是否已经创建,避免重复创建
            Mine userInfo = new Mine("个人信息");
            mineList.add(userInfo);
            Mine parkingRecord = new Mine("停车记录");
            mineList.add(parkingRecord);
            Mine topupRecord = new Mine("充值记录");
            mineList.add(topupRecord);
            Mine setting = new Mine("设置");
            mineList.add(setting);

            CREATED = 1;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        CREATED = 0;    // 将mineList的创建状态置0
    }

    /**
     * 进入登录界面
     */
    private void enterLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 进入个人信息界面
     */
    private void enterUserInfo() {
        QueryUtils queryUtils = new QueryUtils(getContext());
        userName = queryUtils.queryUserName();
        if ("".equals(userName)) {
            final AlertDialog leaveDialog = new AlertDialog.Builder(getContext()).create();
            leaveDialog.setTitle("登录提醒: ");
            leaveDialog.setMessage("您还没有登录,是否先登录?");
            leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveDialog.dismiss();
                            enterLogin();
                        }
                    });
            leaveDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveDialog.dismiss();
                        }
                    });
            leaveDialog.show();
        } else {
            Intent intent = new Intent(getActivity(), InfoActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 进入停车记录界面
     */
    private void enterParkingRecord() {
        ArrayList<ParkingInfo> parkingInfoArrayList = new ArrayList<>();
        QueryUtils queryUtils = new QueryUtils(getContext());
        String userName = queryUtils.queryUserName();

        if (!"".equals(userName)) {
            Intent intent = new Intent(getActivity(), ParkingRecordActivity.class);
            startActivity(intent);
        } else {
            final AlertDialog leaveDialog = new AlertDialog.Builder(getContext()).create();
            leaveDialog.setTitle("登录提醒: ");
            leaveDialog.setMessage("您还没有登录,是否先登录?");
            leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveDialog.dismiss();
                            enterLogin();
                        }
                    });
            leaveDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveDialog.dismiss();
                            ToastUtils.show(getContext(), "取消成功");
                        }
                    });
            leaveDialog.show();
        }

    }

    /**
     * 进入充值记录界面
     */
    private void enterTopupRecord() {
        ArrayList<TopupInfo> topupInfoArrayList = new ArrayList<>();
        QueryUtils queryUtils = new QueryUtils(getContext());
        String userName = queryUtils.queryUserName();

        if ("".equals(userName)) {
            final AlertDialog leaveDialog = new AlertDialog.Builder(getContext()).create();
            leaveDialog.setTitle("登录提醒: ");
            leaveDialog.setMessage("您还没有登录,是否先登录?");
            leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveDialog.dismiss();
                            enterLogin();
                        }
                    });
            leaveDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            leaveDialog.dismiss();
                        }
                    });
            leaveDialog.show();
        } else {
            Intent intent = new Intent(getContext(), TopupRecordActivity.class);
            startActivity(intent);
        }
    }

    /**
     * 进入设置界面
     */
    private void enterSetting() {
       Intent intent = new Intent(getActivity(), SettingActivity.class);
       startActivity(intent);
    }

    /**
     * 更新本地停车记录
     */
    private void updateParkingRecord(String userName) {
        // 向服务器查询并写入本地数据库
        final QueryUtils queryUtils = new QueryUtils(getContext());
        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", userName);
        queryUtils.delParkingRecord(userName);

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).sendHttpPostRequest(URL_MINE_PARKING_RECORD, request,
                    new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            ArrayList<ParkingInfo> parkingInfos = new ArrayList<>();

                            for (int i = 0; i < response.getDataList().size(); i++) {
                                ParkingInfo parkingInfo = new ParkingInfo();
                                parkingInfo.userName = response.getDataList().get(i).get("user_name");
                                parkingInfo.cost = response.getDataList().get(i).get("pl_money");
                                parkingInfo.startTime = response.getDataList().get(i).get("pl_start_time");
                                parkingInfo.endTime = response.getDataList().get(i).get("pl_end_time");

                                parkingInfos.add(parkingInfo);
                            }
                            boolean reslut = queryUtils.addParkingRecord(parkingInfos);

                            /*if (reslut == true) {
                                ToastUtils.show(getActivity(), "插入成功");
                            } else {
                                ToastUtils.show(getActivity(), "插入失败");
                            }*/
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

    private void updateTopupRecord(String userName) {
        // 向服务器查询并写入本地数据库
        final QueryUtils queryUtils = new QueryUtils(getContext());
        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", userName);
        queryUtils.delTopupRecord(userName);

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).sendHttpPostRequest(URL_MINE_TOPUP_RECORD, request,
                    new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            ArrayList<TopupInfo> topupInfos = new ArrayList<>();

                            for (int i = 0; i < response.getDataList().size(); i++) {
                                TopupInfo topupInfo = new TopupInfo();
                                topupInfo.userName = response.getDataList().get(i).get("user_name");
                                topupInfo.money = response.getDataList().get(i).get("tl_money");
                                topupInfo.time = response.getDataList().get(i).get("tl_time");

                                topupInfos.add(topupInfo);
                            }
                            boolean reslut = queryUtils.addTopupRecord(topupInfos);

                            /*if (reslut == true) {
                                ToastUtils.show(getActivity(), "插入成功");
                            } else {
                                ToastUtils.show(getActivity(), "插入失败");
                            }*/
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
