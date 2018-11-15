package com.example.metfone.colorracemetfone.ui.showResultQrCode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.util.DBCheckInSecond;
import com.example.metfone.colorracemetfone.util.DBHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShowResultQrCodeActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView imgWaring;
    private ImageView imgBack;
    private TextView tvTitle;
    private RelativeLayout relativeWaring;
    private Button btnOk;
    private DBHelper dbHelper;
    private boolean flag;
    private String isdnCus;
    private String data;
    private String ticketType;
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_qr_code);


        imgWaring = findViewById(R.id.imgWaring);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        tvTitle = findViewById(R.id.tvTitle);
        relativeWaring = findViewById(R.id.relativeWaring);
        btnOk = findViewById(R.id.btnOk);

        Intent intent = getIntent();
        data = intent.getStringExtra("data");
        String[] separated = data.split(";");

        for (int i = 0; i < separated.length; i++) {
            String[] arrStr = separated[i].split("=");
            if ("isdn ".equals(arrStr[0])) {
                if (arrStr.length > 1) {
                    isdnCus = arrStr[1];
                } else {
                    isdnCus = "";
                }
            } else if ("ticket_type".equals(arrStr[0])) {
                if (arrStr.length > 1) {
                    ticketType = arrStr[1];
                } else {
                    ticketType = "";
                }
            }

            dbHelper = new DBHelper(this);
            flag = dbHelper.checkIsData(isdnCus.trim());

            if (flag) {
                tvTitle.setText(this.getResources().getString(R.string.success));
                imgWaring.setBackground(this.getResources().getDrawable(R.drawable.ic_success));
                relativeWaring.setBackground(this.getResources().getDrawable(R.drawable.cycler_green_success));
                MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.scan_alert);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
            } else {
                tvTitle.setText(this.getResources().getString(R.string.fail));
                imgWaring.setBackground(this.getResources().getDrawable(R.drawable.ic_fail));
                relativeWaring.setBackground(this.getResources().getDrawable(R.drawable.cycler_red_fail));
                MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.scanfail);
                mediaPlayer.start(); // no need to call prepare(); create() does that for you
            }

            btnOk.setOnClickListener(this);

        }
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnOk:
                if (flag){
                    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
                    String currentDateandTime = sdf.format(new Date());
                    boolean isUpdate = dbHelper.updateStatus(isdnCus.trim() , "1");
                    Log.d("result" , "value: ");
                    finish();
                }else {
                    finish();
                }

                break;
            case R.id.imgBack:
                Intent returnIntent = new Intent();
                setResult(4,returnIntent);
                finish();
                break;

        }
    }
}
