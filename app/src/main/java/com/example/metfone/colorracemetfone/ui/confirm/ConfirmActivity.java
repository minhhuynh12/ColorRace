package com.example.metfone.colorracemetfone.ui.confirm;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.confirm.model.GetTicketItem;
import com.example.metfone.colorracemetfone.ui.login.LoginActivity;
import com.example.metfone.colorracemetfone.ui.login.model.CheckOTPItem;
import com.example.metfone.colorracemetfone.ui.login.model.GetOtpItem;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by vitinhHienAnh on 28-10-18.
 */

public class ConfirmActivity extends AppCompatActivity implements View.OnClickListener {
    private Button btnConfirm;
    private Handler handler = new Handler();
    private Runnable runnable;
    private String EVENT_DATE_TIME = "2018-12-31 10:30:00";
    private String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private TextView tvDay;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private ImageView imgCheckBox;
    private LinearLayout llImageCheck;
    private boolean flag = false;
    private Activity mActivity;
    private List<GetTicketItem> items;
    private String otp;
    private String isdn;
    private String status;
    private String qrCode;
    private String typeTicket;
    private String lat;
    private String log;
    ArrayList arrListGift = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        btnConfirm = findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(this);
        mActivity = this;
        Intent i = getIntent();
        arrListGift = i.getStringArrayListExtra("LIST_GIFT");
        status = i.getStringExtra("STATUS");
        qrCode = i.getStringExtra("QR_CODE");
        otp = i.getStringExtra("OTP");
        isdn = i.getStringExtra("ISDN");
        typeTicket = i.getStringExtra("TYPE_TICKET");
        lat = i.getStringExtra("LAT");
        log = i.getStringExtra("LONG");

        init();
        countDownStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        countDownStart();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnConfirm:
                new CallWSAsyncTask().execute("1", isdn , otp);
                break;
            case R.id.llImageCheck:
                if (!flag){
                    flag = true;
                    btnConfirm.setVisibility(View.VISIBLE);
                    imgCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_checked_checkbox));
                }else {
                    flag = false;
                    btnConfirm.setVisibility(View.GONE);
                    imgCheckBox.setBackground(getResources().getDrawable(R.drawable.ic_unchecked_checkbox));
                }
                break;
        }
    }

    private void init(){
        tvDay = findViewById(R.id.tvDay);
        tvHour = findViewById(R.id.tvHour);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSecond);
        imgCheckBox = findViewById(R.id.imgCheckBox);
        llImageCheck =  findViewById(R.id.llImageCheck);

        llImageCheck.setOnClickListener(this);
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

    private class CallWSAsyncTask extends AsyncTask<String, Byte, Integer> {

        private ProgressDialog progress;
        private RequestGetwayWS req;

        public CallWSAsyncTask() {
            req = new RequestGetwayWS(mActivity);
            progress = new ProgressDialog(mActivity);
            progress.setCanceledOnTouchOutside(false);
            progress.setCancelable(false);
            progress.setMessage("Loading...");
            progress.show();
        }

        @Override
        protected Integer doInBackground(String... params) {
            int res = 0;
            boolean isNetwork = CommonActivity.isNetworkConnected(mActivity);
            if (isNetwork) {
                int id = Integer.parseInt(params[0]);
                int resReq = -1;
                try {
                    switch (id) {
                        case 1:
                            req.addParam("isdn", params[1]);
                            req.addParam("otp", params[2]);
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "getTicket", "login",
                                    "getlist", mActivity);
                            break;

                    }
                    res = id * resReq;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return res;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result > 0) {
                String errorGW = req.getErrorCodeGW();
                String errorWS = req.getErrorCode();
                if ((errorGW != null && errorGW.equals("0")) && (errorWS != null && errorWS.equals("00"))) {
                    switch (result) {
                        case 1:
                            Intent intent = new Intent(ConfirmActivity.this , MainActivity.class);
                            intent.putStringArrayListExtra("LIST_GIFT", arrListGift);
                            intent.putExtra("STATUS" , status);
                            intent.putExtra("QR_CODE" , qrCode);
                            intent.putExtra("TYPE_TICKET" , typeTicket);
                            intent.putExtra("LAT" , lat);
                            intent.putExtra("LONG" , log);
                            startActivity(intent);
                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();

                    items =  req.parseXMLToListObject("return", GetTicketItem.class);
                    if (errorDec != null && !errorDec.isEmpty()) {
                        if (errorWS.equals(Constant.TIMEOUT_CODE)) {
                            CommonActivity.createAlertDialog(mActivity, errorDec,
                                    getString(R.string.app_name), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {

                                        }
                                    }).show();
                        } else {
                            CommonActivity.createAlertDialog(mActivity, errorDec, getString(R.string.app_name)).show();
                        }
                    } else {
                        if (items != null){
                            CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageEn(), getString(R.string.app_name)).show();
                        }else {
                            CommonActivity.createAlertDialog(mActivity, getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
                        }
                    }
                }
            } else {// Goi ws that bai
                if (result == 0) {// Do khong co mang
                    CommonActivity.createAlertDialog(mActivity,
                            getString(R.string.errorNetworkAccess), getString(R.string.app_name)).show();
                } else {// Loi khi goi ws
                    CommonActivity.createAlertDialog(mActivity,
                            getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
                }
            }
            progress.dismiss();
        }
    }

}
