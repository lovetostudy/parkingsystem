package com.parkingsystem.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.activity.BaseActivity;
import com.parkingsystem.activity.LoginActivity;
import com.parkingsystem.activity.NavigationActivity;
import com.parkingsystem.activity.TopupActivity;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.QueryUtils;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import org.w3c.dom.Text;

import static com.parkingsystem.utils.Constant.URL_CONTROLLER_ENTER;
import static com.parkingsystem.utils.Constant.URL_CONTROLLER_LEAVE;


public class ControllerFragment extends Fragment {

    private static final String ENTER = "0";
    private static final String LEVAE = "1";
    private static final String NAVIGATION = "2";
    private String FLAG;

    private Button bt_controller_enter;
    private Button bt_controller_leave;
    private Button bt_controller_navigation;
    private Button bt_controller_topup;

    private TextView tvMap;
    private TextView tvTopup;
    private TextView tvEnter;
    private TextView tvLeave;

    String userName = "";
    private CommonRequest request = new CommonRequest();
    private View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_controller, container, false);

        initData();
        enterButtonListner();
        leaveButtonListener();
        navigationButtonListener();
        topupButtonListener();

        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {

        bt_controller_enter = (Button) view.findViewById(R.id.bt_controller_enter);
        bt_controller_leave = (Button) view.findViewById(R.id.bt_controller_leave);
        bt_controller_navigation = (Button) view.findViewById(R.id.bt_controller_navigation);
        bt_controller_topup = (Button) view.findViewById(R.id.bt_controller_topup);

        tvEnter = (TextView) view.findViewById(R.id.tv_controller_enter);
        tvLeave = (TextView) view.findViewById(R.id.tv_controller_leave);
        tvMap = (TextView) view.findViewById(R.id.tv_controller_map);
        tvTopup = (TextView) view.findViewById(R.id.tv_controller_topup);

        FLAG = queryCarState();
        if ("1".equals(FLAG)) {
            tvEnter.setText("已进入停车场");
            tvEnter.setTextColor(Color.RED);
            bt_controller_enter.setEnabled(false);
        }
        if ("0".equals(FLAG)){
            tvLeave.setText("未停车");
            tvLeave.setTextColor(Color.RED);
            bt_controller_leave.setEnabled(false);
        }
    }

    /**
     * 进入按钮点击事件
     */
    private void enterButtonListner() {
        bt_controller_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QueryUtils queryUtils = new QueryUtils(getContext());
                userName = queryUtils.queryUserName();

                if (!"".equals(userName)) {
                    request.addRequestParam("name", userName);
                    request.setRequestCode(ENTER);

                    final AlertDialog enterDialog = new AlertDialog.Builder(getContext()).create();
                    enterDialog.setTitle("停车提醒: ");
                    enterDialog.setMessage("进入停车场?");
                    enterDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    enterDialog.dismiss();
                                    if (getActivity() instanceof BaseActivity) {
                                        ((BaseActivity)getActivity()).sendHttpPostRequest(URL_CONTROLLER_ENTER, request,
                                                new ResponseHandler() {
                                                    @Override
                                                    public void success(CommonResponse response) {

                                                        queryUtils.updateCarState("1", userName);

                                                        tvEnter.setText("已进入停车场");
                                                        tvEnter.setTextColor(Color.RED);
                                                        bt_controller_enter.setEnabled(false);
                                                        tvLeave.setText("结账离开");
                                                        tvLeave.setTextColor(Color.BLACK);
                                                        bt_controller_leave.setEnabled(true);

                                                        ToastUtils.show(getActivity(), "开门成功");
                                                    }

                                                    @Override
                                                    public void error1(CommonResponse response) {

                                                    }

                                                    @Override
                                                    public void error2(CommonResponse response) {

                                                    }

                                                    @Override
                                                    public void fail(String failCode, String failMsg) {
                                                        ToastUtils.show(getActivity(),"开门失败,请重试!");
                                                    }
                                                }, false);
                                    }
                                }
                            });
                    enterDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    enterDialog.dismiss();
                                    ToastUtils.show(getContext(), "取消成功");
                                }
                            });
                    enterDialog.show();
                } else {
                    final AlertDialog loginDialog = new AlertDialog.Builder(getContext()).create();
                    loginDialog.setTitle("登录提醒: ");
                    loginDialog.setMessage("您还未登录,是否先登录?");
                    loginDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginDialog.dismiss();
                                    enterLoginActivity();
                                }
                            });
                    loginDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginDialog.dismiss();
                                }
                            });
                    loginDialog.show();
                }
            }
        });
    }

    /**
     * 结账离开按钮点击事件
     */
    private void leaveButtonListener() {
        bt_controller_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QueryUtils queryUtils = new QueryUtils(getContext());
                userName = queryUtils.queryUserName();

                if (!"".equals(userName)) {
                    request.addRequestParam("name", userName);
                    request.setRequestCode(LEVAE);

                    final AlertDialog leaveDialog = new AlertDialog.Builder(getContext()).create();
                    leaveDialog.setTitle("离开提醒: ");
                    leaveDialog.setMessage("结账离开停车场?");
                    leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    leaveDialog.dismiss();
                                    if (getActivity() instanceof BaseActivity) {
                                        ((BaseActivity)getActivity()).sendHttpPostRequest(URL_CONTROLLER_LEAVE, request,
                                                new ResponseHandler() {
                                                    @Override
                                                    public void success(CommonResponse response) {
                                                        final AlertDialog topupDialog = new AlertDialog.Builder(getContext()).create();
                                                        topupDialog.setTitle("提醒：");
                                                        topupDialog.setMessage(response.getResMsg());
                                                        topupDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
                                                                new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                                        topupDialog.dismiss();
                                                                    }
                                                                });
                                                        topupDialog.show();

                                                        queryUtils.updateCarState("0", userName);
                                                        tvEnter.setText("进入");
                                                        tvEnter.setTextColor(Color.BLACK);
                                                        bt_controller_enter.setEnabled(true);
                                                        tvLeave.setText("未停车");
                                                        tvLeave.setTextColor(Color.RED);
                                                        bt_controller_leave.setEnabled(false);
                                                    }

                                                    @Override
                                                    public void error1(CommonResponse response) {

                                                    }

                                                    @Override
                                                    public void error2(CommonResponse response) {

                                                    }

                                                    @Override
                                                    public void fail(String failCode, String failMsg) {
                                                        ToastUtils.show(getActivity(), "结账失败,请重试!");
                                                    }
                                                }, false);
                                    }
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
                    final AlertDialog loginDialog = new AlertDialog.Builder(getContext()).create();
                    loginDialog.setTitle("登录提醒: ");
                    loginDialog.setMessage("您还未登录,是否先登录?");
                    loginDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginDialog.dismiss();
                                    enterLoginActivity();
                                }
                            });
                    loginDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginDialog.dismiss();
                                }
                            });
                    loginDialog.show();
                }

            }
        });

    }

    /**
     * 导航按钮点击事件
     */
    private void navigationButtonListener() {
        bt_controller_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NavigationActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 充值按钮点击事件
     */
    private void topupButtonListener() {
        bt_controller_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final QueryUtils queryUtils = new QueryUtils(getContext());
                userName = queryUtils.queryUserName();

                if (!"".equals(userName)) {
                    Intent intent = new Intent(getContext(), TopupActivity.class);
                    startActivity(intent);
                } else {
                    final AlertDialog loginDialog = new AlertDialog.Builder(getContext()).create();
                    loginDialog.setTitle("登录提醒: ");
                    loginDialog.setMessage("您还未登录,是否先登录?");
                    loginDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginDialog.dismiss();
                                    enterLoginActivity();
                                }
                            });
                    loginDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取  消",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    loginDialog.dismiss();
                                }
                            });
                    loginDialog.show();
                }
            }
        });
    }

    /**
     * 进入登录界面
     */
    private void enterLoginActivity() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 查询车辆的停留情况
     *
     * @return
     */
    private String queryCarState() {
        QueryUtils queryUtils = new QueryUtils(getContext());
        userName = queryUtils.queryUserName();
        String state = queryUtils.queryCarState(userName);

        return state;
    }

}
