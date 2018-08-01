package com.parkingsystem.fragment;



import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parkingsystem.R;
import com.parkingsystem.utils.ToastUtils;

public class ControllerFragment extends Fragment {

    private Button bt_controller_enter;
    private Button bt_controller_leave;
    private Button bt_controller_navigation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_controller, container, false);

        bt_controller_enter = (Button) view.findViewById(R.id.bt_controller_enter);
        bt_controller_leave = (Button) view.findViewById(R.id.bt_controller_leave);
        bt_controller_navigation = (Button) view.findViewById(R.id.bt_controller_navigation);


        bt_controller_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog enterDialog = new AlertDialog.Builder(getContext()).create();
                enterDialog.setTitle("停车提醒: ");
                enterDialog.setMessage("进入停车场?");
                enterDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                enterDialog.dismiss();
                                ToastUtils.show(getContext(), "进入停车场");
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
            }
        });

        bt_controller_leave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog leaveDialog = new AlertDialog.Builder(getContext()).create();
                leaveDialog.setTitle("离开提醒: ");
                leaveDialog.setMessage("结账离开停车场?");
                leaveDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确  定",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                leaveDialog.dismiss();
                                ToastUtils.show(getContext(), "结账");
                                ToastUtils.show(getContext(), "离开停车场");
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
        });

        bt_controller_navigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getContext(), "导航成功");
            }
        });
        return view;
    }
}
