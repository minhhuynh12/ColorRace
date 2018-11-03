package com.example.metfone.colorracemetfone.ui.login;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.commons.Constant;
import com.example.metfone.colorracemetfone.ui.MainActivityEmploye.MainActivity;
import com.example.metfone.colorracemetfone.ui.SignData.SignDataActivity;
import com.example.metfone.colorracemetfone.ui.confirm.ConfirmActivity;
import com.example.metfone.colorracemetfone.ui.login.model.CheckOTPItem;
import com.example.metfone.colorracemetfone.ui.login.model.GetOtpItem;
import com.example.metfone.colorracemetfone.util.RequestGetwayWS;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity implements View.OnClickListener {
    Button btnLogin;
    private Activity mActivity;
    private Button btnOTP;
    private List<GetOtpItem> itemGetOTP;
    public  List<CheckOTPItem> itemCheckOTP;
    private EditText edOTP;
    private EditText edISDN;
    private String otp;
    private String isdn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivity_login);
        btnLogin = findViewById(R.id.btnLogin);
        btnOTP = findViewById(R.id.btnOTP);
        edOTP = findViewById(R.id.edOTP);
        edISDN = findViewById(R.id.edISDN);

        btnLogin.setOnClickListener(this);
        btnOTP.setOnClickListener(this);
        mActivity = this;

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnLogin:
                otp = edOTP.getText().toString().trim();
                isdn = edISDN.getText().toString().trim();
                if (validate())
                new CallWSAsyncTask().execute("2", isdn , otp);


                break;
            case R.id.btnOTP:
                isdn = edISDN.getText().toString().trim();
                new CallWSAsyncTask().execute("1", isdn);
                break;
        }
    }

    private boolean validate() {
        if ("".equals(isdn)){
            Toast.makeText(this, "Please input phone number" , Toast.LENGTH_SHORT).show();
            return false;
        }
        if ("".equals(otp)){
            Toast.makeText(this, "Please input OTP" , Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
                        case 2:
                            req.addParam("isdn", params[1]);
                            req.addParam("otp", params[2]);
                            resReq = req.sendRequestWS(Constant.BCCS_GW_URL, "checkOtp", "login",
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
                            itemGetOTP =  req.parseXMLToListObject("return", GetOtpItem.class);
                            Toast.makeText(mActivity, "Success" , Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            break;
                        case 2:
                            itemCheckOTP = req.parseXMLToListObject("return" , CheckOTPItem.class);
                            String lat = itemCheckOTP.get(0).getLatStadium();
                            String log = itemCheckOTP.get(0).getLongStadium();
                            ArrayList arrListGift = new ArrayList();

                            if (itemCheckOTP.get(0).getRole().getRoleName().equals("CUSTOMER")){
                                for(int i = 0; i < itemCheckOTP.get(0).getTicket().getLstGift().size(); i++){
                                    arrListGift.add(itemCheckOTP.get(0).getTicket().getLstGift().get(i));
                                }
                                String stattus = itemCheckOTP.get(0).getTicket().getStatus();
                                String qrCode = itemCheckOTP.get(0).getTicket().getQrCode();
                                String typeTicket = itemCheckOTP.get(0).getTicket().getTicketType();
                                if ("0".equals(itemCheckOTP.get(0).getTicket().getStatus())){
                                    Intent intent = new Intent(LoginActivity.this, ConfirmActivity.class);
                                    intent.putExtra("OTP", otp);
                                    intent.putExtra("ISDN", isdn);
                                    intent.putExtra("STATUS" , stattus);
                                    intent.putExtra("QR_CODE" , qrCode);
                                    intent.putExtra("TYPE_TICKET" , typeTicket);
                                    intent.putExtra("LAT" , lat);
                                    intent.putExtra("LONG" , log);
                                    intent.putStringArrayListExtra("LIST_GIFT", arrListGift);
                                    startActivity(intent);
                                }else {
//                                    String stattus = itemCheckOTP.get(0).getTicket().getStatus();
//                                    String qrCode = itemCheckOTP.get(0).getTicket().getQrCode();
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                    intent.putStringArrayListExtra("LIST_GIFT", arrListGift);
                                    intent.putExtra("STATUS" , stattus);
                                    intent.putExtra("QR_CODE" , qrCode);
                                    intent.putExtra("TYPE_TICKET" , typeTicket);
                                    intent.putExtra("LAT" , lat);
                                    intent.putExtra("LONG" , log);
                                    startActivity(intent);
                                }
                            }else {
                                if ("STAFF_SYNC".equals(itemCheckOTP.get(0).getRole().getPermission())){
                                    Intent intent = new Intent(LoginActivity.this , SignDataActivity.class);
                                    intent.putExtra("OTP", otp);
                                    intent.putExtra("ISDN", isdn);
                                    startActivity(intent);
                                }else {

                                }
                            }
                            progress.dismiss();
                            break;
                    }
                } else {
                    String errorDec = req.getErrorDecription();
                    String resultXML = req.getResultXML();

                    itemGetOTP =  req.parseXMLToListObject("return", GetOtpItem.class);
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
                        if (itemGetOTP != null){
                            CommonActivity.createAlertDialog(mActivity, itemGetOTP.get(0).getMessageEn(), getString(R.string.app_name)).show();
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
