package com.example.metfone.colorracemetfone.ui;



import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.metfone.colorracemetfone.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        }
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        changeActionBar();
        setContentView(R.layout.activity_main);
    }

    private void changeActionBar() {
        ActionBar mActionBar = this.getSupportActionBar();
//        ActionBar mActionBar = this.getActionBar();
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.layout_actionbar);

        mActionBar.setDisplayShowCustomEnabled(true);
        mActionBar.setDisplayHomeAsUpEnabled(true);

    }
}
