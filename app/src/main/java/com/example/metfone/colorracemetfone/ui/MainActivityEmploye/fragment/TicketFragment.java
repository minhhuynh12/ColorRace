package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.adapter.TicketAdapter;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.model.TicketItems;
import com.example.metfone.colorracemetfone.ui.maps.MapsShowroomActivity;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class TicketFragment extends Fragment {
    private TicketAdapter mAdapter;
    //    private List<TicketItems> list;
    private RecyclerView recycler;
    private static List<String> list;
    private static String status;
    private static List<String> listClone;
    private SharePreferenceUtils sharedPreferences;
    private Button btnMap;
    LocationManager locationManager;
    String stringLatitude = "", stringLongitude = "";

    public static TicketFragment newInstance(List<String> listStr) {
        TicketFragment fragmentFirst = new TicketFragment();

        list = listStr;
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_ticket, container, false);
        recycler = view.findViewById(R.id.recycler);
        btnMap = view.findViewById(R.id.btnMap);
        sharedPreferences = new SharePreferenceUtils(getActivity());
        status = sharedPreferences.getStatusCustomer();

        mAdapter = new TicketAdapter(getActivity());
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(mLayoutManager);
        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(mAdapter);

        if (!"".equals(list.get(0))) {
            list.add(0, "");
        }
        mAdapter.setData(list, status);


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationManager = (LocationManager) getActivity().getSystemService(getActivity().LOCATION_SERVICE);

                String locationProviders = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                if (locationProviders == null || locationProviders.equals("")){
                    CommonActivity.createDialogYesNo(getActivity(), getActivity().getResources().getString(R.string.turn_on_location), getString(R.string.app_name), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getActivity(), MapsShowroomActivity.class);
                            startActivity(intent);
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            getActivity().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).show();

                }else {
                    Intent intent = new Intent(getActivity(), MapsShowroomActivity.class);
                    startActivity(intent);
                }

            }
        });

        return view;
    }


}
