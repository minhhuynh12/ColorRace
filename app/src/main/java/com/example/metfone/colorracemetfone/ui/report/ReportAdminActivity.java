package com.example.metfone.colorracemetfone.ui.report;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.report.adapter.ReportAdapter;
import com.example.metfone.colorracemetfone.ui.report.model.LstTicketGiftDepartmentItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftDepartmentItem;
import com.example.metfone.colorracemetfone.ui.report.model.TicketGiftItem;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

import java.util.ArrayList;
import java.util.List;

public class ReportAdminActivity extends AppCompatActivity {
    private Activity mActivity;
    private RecyclerView recyclerShowroom;
    private List<TicketGiftDepartmentItem> items;
    private List<TicketGiftItem> itemTicketGift;
    private SharePreferenceUtils sharedPreferences;
    String language;
    private ReportAdapter mAdapter;
    private String isdn;
    private String otp;
    private LinearLayout llBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_admin);
        recyclerShowroom = findViewById(R.id.recyclerShowroom);
        llBack = findViewById(R.id.llBack);

        llBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mActivity = this;
        sharedPreferences = new SharePreferenceUtils(this);
        language = sharedPreferences.getLanguage();

        mAdapter = new ReportAdapter(this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerShowroom.setLayoutManager(mLayoutManager);
        recyclerShowroom.setItemAnimator(new DefaultItemAnimator());
        recyclerShowroom.setAdapter(mAdapter);

        isdn = sharedPreferences.getISDN();
        otp = sharedPreferences.getOTP();

        new CallWSAsyncTask().execute("1", isdn , otp);

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
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "getReportGift", "",
                                    "", mActivity);
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
                            items = req.parseXMLToListObject("ticketGiftDepartment", TicketGiftDepartmentItem.class);
                            itemTicketGift = req.parseXMLToListObject("ticketGift", TicketGiftItem.class);

                            mAdapter.setData(items);

                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();

//                    items = req.parseXMLToListObject("return", TicketGiftItem.class);
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
//                        if (items != null){
//                            if (language.toLowerCase().equals("kh")) {
//                                CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageKh(), getString(R.string.app_name)).show();
//                            }else {
//                                CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageEn(), getString(R.string.app_name)).show();
//                            }
//                        }else {
//                            CommonActivity.createAlertDialog(mActivity, getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                        }
                        CommonActivity.createAlertDialog(mActivity,
                                getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
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
