package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.util.GPSTracker;

import java.util.Locale;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class MapFragment extends Fragment implements View.OnClickListener {
    private ImageView imgMaps;
    private static String lat;
    private static String log;
    LocationManager locationManager;
    String stringLatitude = "", stringLongitude = "", nameOfLocation="";
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
        GPSTracker gpsTracker = new GPSTracker(getActivity());


        locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);
        if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("not found");  // GPS not found
            builder.setMessage("do you want to turn on gps"); // Want to enable?
            builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    getActivity().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                }
            });
            builder.setNegativeButton(R.string.no, null);
            builder.create().show();
        }

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);

        }
        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imgMaps:
                if( !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ) {
                    Toast.makeText(getActivity(), "please! turn on your GPS." , Toast.LENGTH_SHORT).show();
                }else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=11.558587,104.911380&daddr="+ stringLatitude +"," + stringLongitude + ""));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
                break;
        }
    }

}
