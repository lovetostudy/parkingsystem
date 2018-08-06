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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parkingsystem.R;
import com.parkingsystem.activity.BaseActivity;
import com.parkingsystem.activity.HomeActivity;
import com.parkingsystem.activity.InfoActivity;
import com.parkingsystem.activity.LoginActivity;
import com.parkingsystem.activity.ParkingRecordActivity;
import com.parkingsystem.activity.TopupRecordActivity;
import com.parkingsystem.entity.Mine;
import com.parkingsystem.entity.ParkingInfo;
import com.parkingsystem.entity.TopupInfo;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.MineAdapter;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.parkingsystem.utils.Constant.URL_COTROLLER_ENTER;
import static com.parkingsystem.utils.Constant.URL_COTROLLER_LEAVE;
import static com.parkingsystem.utils.Constant.URL_MINE_PARKING_RECORD;
import static com.parkingsystem.utils.Constant.URL_MINE_TOPUP_RECORD;

public class MineFragment extends Fragment {

    private static final int USER_LOGIN = 0;
    private static final int USER_INFO = 1;
    private static final int PARKING_RECORD = 2;
    private static final int TOPUP_RECORD = 3;
    private static final int SETTING = 4;
    private static final int USER_LOGOUT = 5;

    private static int CREATED = 0;

    private List<Mine> mineList = new ArrayList<>();

    private String userName;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case USER_LOGIN:
                    enterLogin();
                    break;
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
                case USER_LOGOUT:
                    enterLogout();
                    break;
                default:
            }
        }
    };

    private Mine mine;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        MineAdapter adapter = new MineAdapter(getContext(), R.layout.mine_item, mineList);
        ListView listView = (ListView) view.findViewById(R.id.mine_list_view);
        listView.setAdapter(adapter);

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
                            case "用户登录":
                                msg.what = USER_LOGIN;
                                break;
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
                            case "退出登录":
                                msg.what = USER_LOGOUT;
                                break;
                            default:
                        }
                        mHandler.sendMessage(msg);
                    }
                }).start();
            }
        });

        return view;
    }

    /**
     * 初始化 ListView
      */
    private void initMine() {
        if (CREATED == 0) {     // 判断是否已经创建,避免重复创建
            Mine userLogin = new Mine("用户登录");
            mineList.add(userLogin);
            Mine userInfo = new Mine("个人信息");
            mineList.add(userInfo);
            Mine parkingRecord = new Mine("停车记录");
            mineList.add(parkingRecord);
            Mine topupRecord = new Mine("充值记录");
            mineList.add(topupRecord);
            Mine setting = new Mine("设置");
            mineList.add(setting);
            Mine userLogout = new Mine("退出登录");
            mineList.add(userLogout);

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
                            ToastUtils.show(getContext(), "取消成功");
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
        final QueryUtils queryUtils = new QueryUtils(getContext());
        String userName = queryUtils.queryUserName();

        if (!"".equals(userName)) {
            parkingInfoArrayList = queryUtils.queryLocalParkingRecord(userName);    // 查询本地记录

            if (parkingInfoArrayList != null) { //有本地记录则直接进入 parkingRecord 界面
                Intent intent = new Intent(getActivity(), ParkingRecordActivity.class);
                startActivity(intent);
                ToastUtils.show(getContext(), "derectory!!!");
            } else {        // 否则向服务器查询并写入本地数据库
                final CommonRequest request = new CommonRequest();
                request.addRequestParam("name", userName);

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

                                        ToastUtils.show(getActivity(), response.getDataList().get(i).get("pl_end_time"));
                                        parkingInfos.add(parkingInfo);
                                    }
                                    boolean reslut = queryUtils.addParkingRecord(parkingInfos);

                                    if (reslut == true) {
                                        ToastUtils.show(getActivity(), "插入成功");
                                    } else {
                                        ToastUtils.show(getActivity(), "插入失败");
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


                ToastUtils.show(getContext(), "enterParkingRecord OK");
            }
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
        final QueryUtils queryUtils = new QueryUtils(getContext());
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
                            ToastUtils.show(getContext(), "取消成功");
                        }
                    });
            leaveDialog.show();
        } else {
            topupInfoArrayList = queryUtils.queryLocalTopupRecord(userName);    // 查询本地记录

            if (topupInfoArrayList != null) { //有本地记录则直接进入 topupRecord 界面
                Intent intent = new Intent(getActivity(), TopupRecordActivity.class);
                startActivity(intent);
                ToastUtils.show(getContext(), "derectory!!!");
            } else {        // 否则向服务器查询并写入本地数据库
                final CommonRequest request = new CommonRequest();
                request.addRequestParam("name", userName);

                if (getActivity() instanceof BaseActivity) {
                    ((BaseActivity) getActivity()).sendHttpPostRequest(URL_MINE_TOPUP_RECORD, request,
                            new ResponseHandler() {
                                @Override
                                public void success(CommonResponse response) {
                                    ArrayList<TopupInfo> topupInfos = new ArrayList<>();

                                    for (int i = 0; i < response.getDataList().size(); i++) {
                                        TopupInfo topupInfo = new TopupInfo();
                                        topupInfo.userName = response.getDataList().get(i).get("user_name");
                                        topupInfo.time = response.getDataList().get(i).get("tl_money");
                                        topupInfo.money = response.getDataList().get(i).get("tl_time");

                                        ToastUtils.show(getActivity(), response.getDataList().get(i).get("tl_time"));
                                        topupInfos.add(topupInfo);
                                    }
                                    boolean reslut = queryUtils.addTopupRecord(topupInfos);

                                    if (reslut == true) {
                                        ToastUtils.show(getActivity(), "插入成功");
                                    } else {
                                        ToastUtils.show(getActivity(), "插入失败");
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
    }
    /**
     * 进入设置界面
     */
    private void enterSetting() {
        ToastUtils.show(getContext(), "enterSetting OK");
    }

    /**
     * 退出登录
     */
    private void enterLogout() {
        QueryUtils queryUtils = new QueryUtils(getContext());
        userName = queryUtils.queryUserName();

        if ("".equals(userName)) {
            enterLogin();
        } else {
            queryUtils.delUserInfo(userName);
            ToastUtils.show(getContext(), "delete OK");
        }
    }
}
