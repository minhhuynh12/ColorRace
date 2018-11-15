package com.example.metfone.colorracemetfone.ui.SignData;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.Chart.ChartActivity;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.SignData.model.ListIsdnItem;
import com.example.metfone.colorracemetfone.ui.SignData.model.SignDataItem;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.ui.confirm.model.GetTicketItem;
import com.example.metfone.colorracemetfone.util.DBHelper;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class SignDataActivity extends AppCompatActivity {
    private String otp;
    private String isdn;
    private Activity mActivity;
    private List<SignDataItem> listSign;
    private DBHelper mydb;
    private String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
    private String EVENT_DATE_TIME;
    ProgressBar progressDoalog;
    ProgressDialog progressdialog;
    private UploadCallbacks mListener;
    private String permissoin;
    private int totalIsdn;
    String currentDateandTime;
    public static int START_ACTIVITY_FOR_RESULT_PIN = 94;

    int pStatus = 0;
    TextView tv;
    ProgressBar mProgress;
    List<String> listStr;

    private SharePreferenceUtils sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_data);
        sharedPreferences = new SharePreferenceUtils(this);

        otp = sharedPreferences.getOTP();
        isdn = sharedPreferences.getISDN();

        mActivity = this;

        mydb = new DBHelper(this);
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        currentDateandTime = sdf.format(new Date());


        new CallWSAsyncTask().execute("1", isdn, otp);

        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.drawable.circular_progress);
        mProgress = (ProgressBar) findViewById(R.id.circularProgressbar);
        mProgress.setProgress(0);   // Main Progress
        mProgress.setSecondaryProgress(100); // Secondary Progress
        mProgress.setMax(100); // Maximum Progress
        mProgress.setProgressDrawable(drawable);
        tv = (TextView) findViewById(R.id.tv);
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
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "syncData", "login", "getlist", mActivity);
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
                            listSign = req.parseXMLToListObject("return", SignDataItem.class);
                            Log.d("show", "value: " + listSign.get(0).getLstIsdn());
                            progress.dismiss();
                            parseJson(listSign.get(0).getLstIsdn());


                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();

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
//                        if (items != null){
//                            CommonActivity.createAlertDialog(mActivity, items.get(0).getMessageEn(), getString(R.string.app_name)).show();
//                        }else {
//                            CommonActivity.createAlertDialog(mActivity, getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                        }
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

    private void parseJson(String lstIsdn) {
        Type listType = new TypeToken<List<String>>() {
        }.getType();
        try {
            JSONObject object = new JSONObject(lstIsdn);
            listStr = (new Gson().fromJson(object.getString("lstIsdn"), listType));
            long uploaded = 0;
            boolean isList = mydb.checkSignData();
            if (isList) {
                mydb.deleteTable();
            }

            new MyTask().execute(listStr.size());

//            for (int i = 0; i < listStr.size(); i++) {
//                mydb.insertContact(listStr.get(i));
//                new ProgressUpdater(uploaded, listStr.size());D

//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

//        Log.d("showList", "list: "+ listIsdn);

    }

    class MyTask extends AsyncTask<Integer, Integer, String> {
        @Override
        protected String doInBackground(Integer... params) {
            for (; pStatus < params[0]; pStatus++) {
                mydb.insertContact(listStr.get(pStatus), "0", currentDateandTime);
                publishProgress((int) ((pStatus / (float) params[0]) * 100));
                if (isCancelled()) break;
            }
            return "Task Completed.";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent intent = new Intent(SignDataActivity.this, ChartActivity.class);
            startActivityForResult(intent, START_ACTIVITY_FOR_RESULT_PIN);
        }

        @Override
        protected void onPreExecute() {
            tv.setText("Task Starting...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            tv.setText(values[0] / listSign.size() + "%");
            mProgress.setProgress(values[0]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_ACTIVITY_FOR_RESULT_PIN) {
            if (resultCode == 12) {
                Intent returnIntent = new Intent();
                setResult(12, returnIntent);
                finish();
            }
            finish();

        }
    }
}
