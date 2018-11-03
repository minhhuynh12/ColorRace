package com.example.metfone.colorracemetfone.ui.MainActivityEmploye;



import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.CreateQrCode.CreateQrCodeActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.Adapter.MainActivityAdapter;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.MapFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.NightRaceFragment;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment.TicketFragment;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    MainActivityAdapter adapter;
    FrameLayout frameMain;
    RelativeLayout llTicket;
    RelativeLayout llNightRace;
    RelativeLayout llMap;
    View vTickket;
    View vNightRace;
    View vMap;
    TextView tvTicket;
    TextView tvNightRace;
    TextView tvMaps;
    CardView cardView;
    List<String> arrListGift;
    private String status;
    private Runnable runnable;
    private String EVENT_DATE_TIME = "2018-12-31 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private Handler handler = new Handler();
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private TextView tvTypeTicket;
    private ImageView imgCreateQR;
    private ImageView imgQr;
    private String qrCode;
    private String typeTicket;
    private String lat;
    private String log;
    @Override
    public void onBackPressed() {
        Log.d("here" , "show");
        finish();

    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        arrListGift = new ArrayList();
        Intent i = getIntent();
        arrListGift = i.getStringArrayListExtra("LIST_GIFT");
        status = i.getStringExtra("STATUS");
        qrCode = i.getStringExtra("QR_CODE");
        typeTicket = i.getStringExtra("TYPE_TICKET");
        lat = i.getStringExtra("LAT");
        log = i.getStringExtra("LONG");
        tvTypeTicket.setText(typeTicket);

        adapter = new MainActivityAdapter(getSupportFragmentManager());
        countDownStart();
        createQrCode();

        TicketFragment fragment = TicketFragment.newInstance(arrListGift , status);
        swichFragemnt(fragment);

    }

    private void createQrCode(){
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(qrCode, BarcodeFormat.QR_CODE, 100, 100);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgQr.setImageBitmap(bitmap);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }

    private void init(){
        llTicket = (RelativeLayout) findViewById(R.id.llTicket);
        llNightRace = (RelativeLayout) findViewById(R.id.llNightRace);
        llMap = (RelativeLayout) findViewById(R.id.llMap);
        tvTicket = (TextView) findViewById(R.id.tvTicket);
        tvNightRace = (TextView) findViewById(R.id.tvNightRace);
        tvMaps = (TextView) findViewById(R.id.tvMaps);
        tvTypeTicket = (TextView) findViewById(R.id.tvTypeTicket);
        frameMain =  findViewById(R.id.frameMain);
        cardView = (CardView) findViewById(R.id.cardView);
        tvDay = findViewById(R.id.tvDay);
        tvHour = findViewById(R.id.tvHour);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSecond);
        imgCreateQR = findViewById(R.id.imgCreateQR);
        imgQr = findViewById(R.id.imgQr);

        vTickket =  findViewById(R.id.vTickket);
        vNightRace =  findViewById(R.id.vNightRace);
        vMap =  findViewById(R.id.vMap);

        llTicket.setOnClickListener(this);
        llNightRace.setOnClickListener(this);
        llMap.setOnClickListener(this);
        imgCreateQR.setOnClickListener(this);
    }

    private void swichFragemnt (Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameMain, fragment,"findThisFragment")
                .addToBackStack(null)
                .commit();
    }

    private void countDownStart() {
        runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    handler.postDelayed(this, 1000);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                    Date event_date = dateFormat.parse(EVENT_DATE_TIME);
                    Date current_date = new Date();
                    if (!current_date.after(event_date)) {
                        long diff = event_date.getTime() - current_date.getTime();
                        long Days = diff / (24 * 60 * 60 * 1000);
                        long Hours = diff / (60 * 60 * 1000) % 24;
                        long Minutes = diff / (60 * 1000) % 60;
                        long Seconds = diff / 1000 % 60;
                        //
                        tvDay.setText(String.format("%02d", Days));
                        tvHour.setText(String.format("%02d", Hours));
                        tvMinute.setText(String.format("%02d", Minutes));
                        tvSecond.setText(String.format("%02d", Seconds));
                    } else {
//                        linear_layout_1.setVisibility(View.VISIBLE);
//                        linear_layout_2.setVisibility(View.GONE);
                        handler.removeCallbacks(runnable);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        handler.postDelayed(runnable, 0);
    }

    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.llTicket:
                TicketFragment fragment = TicketFragment.newInstance(arrListGift, status);
                swichFragemnt( fragment);
                setBackground(vTickket , getResources().getColor(R.color.color_title) , tvTicket ,  getResources().getColor(R.color.color_title) );
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray), tvNightRace, getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray) , tvMaps , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.VISIBLE);
                break;
            case R.id.llNightRace:
                NightRaceFragment fragment2 = new NightRaceFragment();
                swichFragemnt( fragment2);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_title) , tvNightRace , getResources().getColor(R.color.color_title));
                setBackground(vMap , getResources().getColor(R.color.color_input_gray), tvMaps , getResources().getColor(R.color.color_black));
                cardView.setVisibility(View.GONE);
                break;
            case R.id.llMap:
                MapFragment fragment3 = MapFragment.newInstance(lat , log);
                swichFragemnt( fragment3);
                setBackground(vTickket , getResources().getColor(R.color.color_input_gray), tvTicket, getResources().getColor(R.color.color_black));
                setBackground(vNightRace , getResources().getColor(R.color.color_input_gray) , tvNightRace , getResources().getColor(R.color.color_black));
                setBackground(vMap , getResources().getColor(R.color.color_title),tvMaps ,  getResources().getColor(R.color.color_title));
                cardView.setVisibility(View.GONE);
                break;
            case R.id.imgCreateQR:
                Intent intent = new Intent(MainActivity.this, CreateQrCodeActivity.class);
                intent.putExtra("QR_CODE", qrCode);
                startActivity(intent);
                break;
        }
    }

    private void setBackground(View view , int colorView , TextView textView , int colorText){
        view.setBackgroundColor(colorView);
        textView.setTextColor(colorText);
    }
}
