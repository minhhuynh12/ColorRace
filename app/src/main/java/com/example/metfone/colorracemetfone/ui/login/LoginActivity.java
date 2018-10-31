package com.example.metfone.colorracemetfone.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;

public class LoginActivity extends Activity implements View.OnClickListener{
    Button btnLogin;
    private Activity mActivity;
    private Button btnOTP;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnOTP = findViewById(R.id.btnOTP);
        btnLogin.setOnClickListener(this);
        btnOTP.setOnClickListener(this);
        mActivity = this;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnLogin:
                Intent intent = new Intent(LoginActivity.this , ConfirmActivity.class);
                startActivity(intent);

                break;
            case R.id.btnOTP:
                new CallWSAsyncTask().execute("1", "0978472155");
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
                            req.addParam("isdn", params[1]);


                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "getOtp", "login",
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
                if ((errorGW != null && errorGW.equals("0")) && (errorWS != null && errorWS.equals("0"))) {
                    switch (result) {
                        case 1:
                        progress.dismiss();
                            break;
                    }
                }
//                else {
//                    String errorDec = req.getErrorDecription();
//
//                    if (errorDec != null && !errorDec.isEmpty()) {
//                        if (errorWS.equals(Constant.TIMEOUT_CODE)) {
//                            CommonActivity.createAlertDialog(mActivity, "[" + req.getErrorCodeGW() + "] " + errorDec,
//                                    getString(R.string.app_name), new View.OnClickListener() {
//
//                                        @Override
//                                        public void onClick(View arg0) {
//                                            MainActivity.backToLogin(mActivity);
//                                        }
//                                    }).show();
//                        } else {
//                            CommonActivity.createAlertDialog(mActivity, "[" + req.getErrorCodeGW() + "] " + errorDec, getString(R.string.app_name)).show();
//                        }
//                    } else {
//                        CommonActivity.createAlertDialog(mActivity, "[" + req.getErrorCodeGW() + "] "
//                                + getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                    }
//                }
//            } else {// Goi ws that bai
//                if (result == 0) {// Do khong co mang
//                    CommonActivity.createAlertDialog(mActivity,
//                            getString(R.string.errorNetworkAccess), getString(R.string.app_name)).show();
//                } else {// Loi khi goi ws
//                    CommonActivity.createAlertDialog(mActivity,
//                            getString(R.string.errorCallWebervice), getString(R.string.app_name)).show();
//                }
//            }
                progress.dismiss();
            }
        }
    }

}
