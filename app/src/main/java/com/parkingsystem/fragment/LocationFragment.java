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

import static com.parkingsystem.utils.Constant.URL_CONTROLLER_LEAVE;
import static com.parkingsystem.utils.Constant.URL_LOCATION_INFO;

public class LocationFragment extends Fragment {

    private TextView spaceLabel;
    private TextView spaceNumber;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_location_piture);
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
            ((BaseActivity)getActivity()).sendHttpPostRequest(URL_LOCATION_INFO, request,
                    new ResponseHandler() {
                        @Override
                        public void success(CommonResponse response) {
                            int number = 0;
                            for (int i = 0; i < response.getDataList().size(); i++) {
                                if ("1".equals(response.getDataList().get(i).get("park_state"))) {
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
}
