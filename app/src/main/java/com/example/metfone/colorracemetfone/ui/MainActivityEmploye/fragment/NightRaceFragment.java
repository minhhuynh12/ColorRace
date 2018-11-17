package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.confirm.ShowConfirmActivity;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class NightRaceFragment extends Fragment {
    private ImageView imgParticipation;
    private SharePreferenceUtils sharePreferenceUtils;
    public static NightRaceFragment newInstance() {
        NightRaceFragment fragmentFirst = new NightRaceFragment();
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_night_race , container , false);
        imgParticipation = view.findViewById(R.id.imgParticipation);
        sharePreferenceUtils = new SharePreferenceUtils(getActivity());
        String language = sharePreferenceUtils.getLanguage();
        if ("kh".equals(language)){
            imgParticipation.setBackground(this.getResources().getDrawable(R.drawable.nightrace_cofirm_kh));
        }else {
            imgParticipation.setBackground(this.getResources().getDrawable(R.drawable.nightrace_cofirm_en));
        }

        imgParticipation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ShowConfirmActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
