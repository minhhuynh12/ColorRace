package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.metfone.colorracemetfone.R;

import java.util.Locale;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class MapFragment extends Fragment implements View.OnClickListener {
    private ImageView imgMaps;
    private static String lat;
    private static String log;
    public static MapFragment newInstance(String latStr , String logStr) {
        MapFragment fragmentFirst = new MapFragment();
        lat = latStr;
        log = logStr;
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map , container , false);
        imgMaps = view.findViewById(R.id.imgMaps);
        imgMaps.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imgMaps:
                String uri = String.format(Locale.ENGLISH, "geo:%s,%s", lat, log);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);
                break;
        }
    }
}
