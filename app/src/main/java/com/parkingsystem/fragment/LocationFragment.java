package com.parkingsystem.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parkingsystem.R;
import com.parkingsystem.activity.BaseActivity;
import com.parkingsystem.utils.CommonRequest;
import com.parkingsystem.utils.CommonResponse;
import com.parkingsystem.utils.ResponseHandler;
import com.parkingsystem.utils.ToastUtils;

import java.util.ArrayList;

import static com.parkingsystem.utils.Constant.URL_CONTROLLER_LEAVE;
import static com.parkingsystem.utils.Constant.URL_LOCATION_INFO;

public class LocationFragment extends Fragment {

    private TextView spaceLabel;
    private TextView spaceNumber;
    private ImageView carOne;
    private ImageView carTwo;
    private ImageView carThree;
    private ImageView carFour;
    private ImageView carFive;
    private ImageView carSix;

/*    private String[] parkingStateList = {"0", "1", "1", "1", "0", "1"};*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        carOne = (ImageView) view.findViewById(R.id.car_one);
        carTwo = (ImageView) view.findViewById(R.id.car_two);
        carThree = (ImageView) view.findViewById(R.id.car_three);
        carFour = (ImageView) view.findViewById(R.id.car_four);
        carFive = (ImageView) view.findViewById(R.id.car_five);
        carSix = (ImageView) view.findViewById(R.id.car_six);
        spaceLabel = (TextView) view.findViewById(R.id.space_label);
        spaceNumber = (TextView) view.findViewById(R.id.space_number);

        updateLocationInfo();
        return view;
    }

    /**
     * 更新停车场车位信息
     */
    private void updateLocationInfo() {
        final CommonRequest request = new CommonRequest();

        if (getActivity() instanceof BaseActivity) {
            ((BaseActivity) getActivity()).sendHttpPostRequest(URL_LOCATION_INFO, request,
                    new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            int number = 0;
                            for (int i = 0; i < response.getDataList().size(); i++) { // 0表示空余
                                if ("0".equals(response.getDataList().get(i).get("park_state"))) {
                                    setCarState(response.getDataList().get(i).get("park_id"));
                                    number++;
                                }
                            }
                            spaceNumber.setText("" + number);
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

    /**
     * 设置车位可见与否
     *
     * @param i
     */
    private void setCarState(String i) {
        switch (i) {
            case "8888":
                carOne.setVisibility(View.GONE);
                break;
            case "6666":
                carTwo.setVisibility(View.GONE);
                break;
            case "5555":
                carThree.setVisibility(View.GONE);
                break;
            case "3333":
                carFour.setVisibility(View.GONE);
                break;
            case "2222":
                carFive.setVisibility(View.GONE);
                break;
            case "1111":
                carSix.setVisibility(View.GONE);
                break;
            default:
        }
    }
}
