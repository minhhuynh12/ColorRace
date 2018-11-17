package com.example.metfone.colorracemetfone.ui.confirm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

public class ShowConfirmActivity extends AppCompatActivity {
    private ImageView imgBack;
    private ImageView imgFollow;
    private SharePreferenceUtils sharePreferenceUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_confirm);
        sharePreferenceUtils = new SharePreferenceUtils(this);
        String language = sharePreferenceUtils.getLanguage();
        imgFollow = findViewById(R.id.imgFollow);
        if ("kh".equals(language)){
            imgFollow.setBackground(this.getResources().getDrawable(R.drawable.nightrace_cofirm_kh));
        }else {
            imgFollow.setBackground(this.getResources().getDrawable(R.drawable.nightrace_cofirm_en));
        }

        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
