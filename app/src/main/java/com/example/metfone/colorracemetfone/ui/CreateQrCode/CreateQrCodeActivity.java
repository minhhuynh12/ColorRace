package com.example.metfone.colorracemetfone.ui.CreateQrCode;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.commons.CommonActivity;
import com.example.metfone.colorracemetfone.ui.login.model.QrCodeItem;
import com.example.metfone.colorracemetfone.util.DbQrCode;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class CreateQrCodeActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView imgCreateQr;
    private ImageView imgBack;
    private SharePreferenceUtils sharePreferenceUtils;
    private String qrInfo;
    private QrCodeItem qrCodeItem;
    private DbQrCode dbQrCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr_code);
        imgCreateQr = findViewById(R.id.imgCreateQr);
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        sharePreferenceUtils = new SharePreferenceUtils(this);
        dbQrCode = new DbQrCode(this);
        qrCodeItem = new QrCodeItem();
        String isdn = sharePreferenceUtils.getISDN_LOGIN();
        qrCodeItem = dbQrCode.getQrCode(isdn);
        String flag = sharePreferenceUtils.getFlagQrCode();
        final ProgressDialog progressdialog = new ProgressDialog(this);
        progressdialog.setMessage("Please Wait....");
        progressdialog.setCancelable(false);
        progressdialog.show();

        if ("1".equals(flag)){
            qrInfo = qrCodeItem.getQrCode();
        }else {
            qrInfo = sharePreferenceUtils.getQrCode();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                if (qrInfo != null){
                    //generate qr code
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(qrInfo, BarcodeFormat.QR_CODE, 1000, 1000);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        final Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgCreateQr.setVisibility(View.VISIBLE);
                                imgCreateQr.setImageBitmap(bitmap);
                            }
                        });
                        progressdialog.dismiss();
                    } catch (Exception ex) {
                        Log.d("Error", ex.getMessage());
                    }
                }else {
                    progressdialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgCreateQr.setVisibility(View.GONE);
                        }
                    });
                    CommonActivity.createAlertDialog(CreateQrCodeActivity.this, CreateQrCodeActivity.this.getResources().getString(R.string.errorNetworkAccess),
                            getString(R.string.app_name), new View.OnClickListener() {
                                @Override
                                public void onClick(View arg0) {
                                    finish();
                                }
                            }).show();
                }
            }
        });
        t.start();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.imgBack:
                finish();
                break;
        }
    }
}
