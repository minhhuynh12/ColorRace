package com.example.metfone.colorracemetfone.ui.showResultQrCode;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.util.DBHelper;

public class ShowResultQrCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgBack;
    private ImageView imgWaring;
    private TextView tvTitle;
    private RelativeLayout relativeWaring;
    private Button btnOk;
    private DBHelper dbHelper;
    private boolean flag;
    private String isdnCus;
    private String data;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qr_code);

        imgBack = findViewById(R.id.imgBack);
        imgWaring = findViewById(R.id.imgWaring);
        tvTitle = findViewById(R.id.tvTitle);
        relativeWaring = findViewById(R.id.relativeWaring);
        btnOk = findViewById(R.id.btnOk);

        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        String[] separated = isdnCus.split(";");

        for(int i = 0; i < separated.length; i++){
            String[] arrStr = separated[i].split("=");
            if ("isdn ".equals(arrStr[0])){
                isdnCus = arrStr[1];
            }
        }

        dbHelper = new DBHelper(this);
        flag = dbHelper.checkIsData(isdnCus);

        if (flag){
            tvTitle.setText(this.getResources().getString(R.string.success));
            imgWaring.setBackground(this.getResources().getDrawable(R.drawable.ic_success));
            relativeWaring.setBackground(this.getResources().getDrawable(R.drawable.cycler_green_success));
        }else {
            tvTitle.setText(this.getResources().getString(R.string.fail));
            imgWaring.setBackground(this.getResources().getDrawable(R.drawable.ic_fail));
            relativeWaring.setBackground(this.getResources().getDrawable(R.drawable.cycler_red_fail));
        }

        btnOk.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnOk:
                finish();
                break;
            case R.id.imgBack:
                finish();
                break;
        }
    }
}
