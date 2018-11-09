package com.example.metfone.colorracemetfone.ui.informationTicket;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.Chart.model.ChartItem;
import com.example.metfone.colorracemetfone.ui.Chart.model.TicketGiftItem;
import com.example.metfone.colorracemetfone.ui.informationTicket.model.InfoItem;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class InformationTicketActivity extends AppCompatActivity implements View.OnClickListener {
    private String otp;
    private String isdn;
    private Activity mActivity;
    private EditText edCustomer;
    private EditText edTicketType;
    private EditText edReceiveTime;
    private EditText edGift;
    private EditText edStatus;
    private ImageView imgBack;
    private String isdnCus;
    private String strISDN;
    private Button btnGivingGifts;
    private String ticketType;
    private String receive_gift_date;
    private String gift;
    private String status;
    String language;
    private SharePreferenceUtils sharedPreferences;
    private List<InfoItem> infoItems;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_ticket);
        edCustomer = findViewById(R.id.edCustomer);
        imgBack = findViewById(R.id.imgBack);
        btnGivingGifts = findViewById(R.id.btnGivingGifts);
        edCustomer = findViewById(R.id.edCustomer);
        edTicketType = findViewById(R.id.edTicketType);
        edReceiveTime = findViewById(R.id.edReceiveTime);
        edGift = findViewById(R.id.edGift);
        edStatus = findViewById(R.id.edStatus);

        sharedPreferences = new SharePreferenceUtils(this);
        language = sharedPreferences.getLanguage();

        Intent intent = getIntent();
        otp = intent.getStringExtra("OTP");
        isdn = intent.getStringExtra("ISDN");
        strISDN = intent.getStringExtra("data");
        String[] separated = strISDN.split(";");

        for (int i = 0; i < separated.length; i++) {
            String[] arrStr = separated[i].split("=");
            if ("isdn ".equals(arrStr[0])) {
                isdnCus = arrStr[1];
                isdnCus = isdnCus.replaceAll(" ", "");
            } else if ("ticket_type".equals(arrStr[0])) {
                ticketType = arrStr[1];
            } else if ("receive_gift_date".equals(arrStr[0])) {
                receive_gift_date = arrStr[1];
            } else if ("gift".equals(arrStr[0])) {
                String strGift;
                strGift = arrStr[1];
                String[] arrayStr;
                gift = strGift.replace("{", "");
                gift = gift.replace("}", "");
                arrayStr = gift.split(",");
                edGift.setText(Arrays.toString(arrayStr).replaceAll("\\[|\\]", "").replaceAll("," , "\n"));
            } else if (" status".equals(arrStr[0])) {
                status = arrStr[1];
            }
        }

        edCustomer.setText(isdnCus);
        edTicketType.setText(ticketType);
        edReceiveTime.setText(receive_gift_date);

        if ("2".equals(status)){
            edStatus.setText(this.getResources().getString(R.string.already_received));
        }else {
            edStatus.setText(this.getResources().getString(R.string.not_yet_received));
        }


        mActivity = this;
        btnGivingGifts.setOnClickListener(this);
        imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnGivingGifts:
                new CallWSAsyncTask().execute("1", isdn, isdnCus, otp);
                break;
            case R.id.imgBack:
                finish();
                break;
        }
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
                            req.addParam("isdnStaff", params[1]);
                            req.addParam("isdnCus", params[2]);
                            req.addParam("otp", params[3]);
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "givingGift", "Chart",
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
                            infoItems = req.parseXMLToListObject("return", InfoItem.class);
                            if (infoItems != null) {
                                if (language.toLowerCase().equals("kh")) {
                                    CommonActivity.createAlertDialog(mActivity, infoItems.get(0).getMessageKh(), getString(R.string.app_name), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {
                                            finish();
                                        }
                                    }).show();
                                } else {
                                    CommonActivity.createAlertDialog(mActivity, infoItems.get(0).getMessageEn(), getString(R.string.app_name), new View.OnClickListener() {
                                        @Override
                                        public void onClick(View arg0) {
                                            finish();
                                        }
                                    }).show();
                                }

                            }
                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();
                    infoItems = req.parseXMLToListObject("return", InfoItem.class);
//                    items =  req.parseXMLToListObject("return", GetTicketItem.class);
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
                        if (infoItems != null) {
                            if (language.toLowerCase().equals("kh")) {
                                CommonActivity.createAlertDialog(mActivity, infoItems.get(0).getMessageKh(), getString(R.string.app_name), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        finish();
                                    }
                                }).show();
                            } else {
                                CommonActivity.createAlertDialog(mActivity, infoItems.get(0).getMessageEn(), getString(R.string.app_name), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View arg0) {
                                        finish();
                                    }
                                }).show();
                            }
                        } else {
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
