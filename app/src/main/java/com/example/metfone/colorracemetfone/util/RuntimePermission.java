package com.example.metfone.colorracemetfone.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class RuntimePermission {
    public static final int REQUEST_PHONE_STATE_PERMISSION_CODE = 6;
    public static boolean CheckingPermissionIsEnabledOrNot(Context context) {


        int FristPermissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION);
        int FourPermissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int FivePermissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE);


        return FristPermissionResult == PackageManager.PERMISSION_GRANTED && FourPermissionResult == PackageManager.PERMISSION_GRANTED && FivePermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    public static void requestReadAndPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE , Manifest.permission.ACCESS_FINE_LOCATION
        }, REQUEST_PHONE_STATE_PERMISSION_CODE);
    }
}
