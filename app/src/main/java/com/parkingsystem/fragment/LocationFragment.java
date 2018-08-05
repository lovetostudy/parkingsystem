package com.parkingsystem.fragment;




import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.parkingsystem.R;

public class LocationFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_location, container, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.iv_location_piture);

        return view;
    }
}
