package com.example.metfone.colorracemetfone.ui.CreateQrCode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;


import com.example.metfone.colorracemetfone.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;


public class CreateQrCodeActivity extends AppCompatActivity {
    private ImageView imgCreateQr;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_qr_code);
        imgCreateQr = findViewById(R.id.imgCreateQr);

        Intent i = getIntent();
        String qrInfo = i.getStringExtra("QR_CODE");
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

        try {

            BitMatrix bitMatrix = multiFormatWriter.encode(qrInfo, BarcodeFormat.QR_CODE, 1000, 1000);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imgCreateQr.setImageBitmap(bitmap);
        } catch (Exception ex) {
            Log.d("Error", ex.getMessage());
        }
    }
}
