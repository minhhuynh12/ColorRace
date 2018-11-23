package com.example.metfone.colorracemetfone.ui.maps;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.BuddhistCalendar;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.maps.model.MackerItem;
import com.example.metfone.colorracemetfone.util.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Locale;

public class MapsShowroomActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener, GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnMarkerClickListener,
        GoogleMap.OnCameraIdleListener {
    GoogleMap mMap;
    LocationManager locationManager;
    String stringLatitude = "", stringLongitude = "";

    ArrayList<MackerItem> markersArray;
    private LinearLayout llbackMaps;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapViewShowroom);
        mapFragment.getMapAsync(this);
        GPSTracker gpsTracker = new GPSTracker(this);
        llbackMaps = findViewById(R.id.llbackMaps);

        if (gpsTracker.canGetLocation()) {
            stringLatitude = String.valueOf(gpsTracker.latitude);
            stringLongitude = String.valueOf(gpsTracker.longitude);
        }

        llbackMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnInfoWindowClickListener(this);
        mMap.setOnMarkerClickListener(this);

        markersArray = new ArrayList<MackerItem>();
        markersArray.add(new MackerItem(11.562888, 104.877825, "PNP05-TeukTlar", "PNP04", "7:30AM-7:00PM"));
        markersArray.add(new MackerItem(11.585695, 104.918565, "PNP08-Chrouy ChongVa", "PNP06", "7:30AM-7:00PM"));
        markersArray.add(new MackerItem(11.550330, 104.904590, "PNP11-Toul Svay Prey1", "PNP04", "7:30AM-7:00PM"));
        markersArray.add(new MackerItem(11.55784, 104.9405, "PNP28-KohPich", "PNP04", "4:00PM-9:00PM"));

        for (int i = 0; i < markersArray.size(); i++) {

            createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle());
        }
    }

    protected Marker createMarker(double latitude, double longitude, String title) {
        LatLng stadium = new LatLng(latitude, longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(stadium, 11));

        int height = 100;
        int width = 70;
        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.location_icon_metfone);
        Bitmap b = bitmapdraw.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        Bitmap bitmap = createCustomMarker(MapsShowroomActivity.this, title);


        Marker myMarker = mMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .icon(BitmapDescriptorFactory.fromBitmap(bitmap)));

        return myMarker;
    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveStarted(int reason) {

        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onCameraMove() {
        Toast.makeText(this, "The camera is moving.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMoveCanceled() {
        Toast.makeText(this, "Camera movement canceled.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraIdle() {
        Toast.makeText(this, "The camera has stopped moving.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        CameraUpdate center = CameraUpdateFactory.newLatLngZoom(
                marker.getPosition(), 12);
        mMap.moveCamera(center);
        String code = marker.getTitle();
        for (int i = 0; i < markersArray.size(); i++) {
            if (code != null) {
                if (code.equals(markersArray.get(i).getTitle())) {
                    actionChoice(code, markersArray.get(i).getTime(), marker.getPosition().latitude, marker.getPosition().longitude);
                }
            }
        }

        return true;
    }

    private void actionChoice(final String code, String time, double lat, double log) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.pop_up_showroom);
        WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
        wmlp.gravity = Gravity.TOP | Gravity.CENTER;

        final String strLat = String.valueOf(lat);
        final String strLong = String.valueOf(log);

        Display display = this.getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        float height = size.y;
        float centerY = height / 2;

//        wmlp.x = 100; // x position
        wmlp.y = (int) centerY + 25; // y position
        TextView tvAddress = dialog.findViewById(R.id.tvAddress);
        TextView tvtimeOpen = dialog.findViewById(R.id.tvtimeOpen);
        tvAddress.setText(code);
        tvtimeOpen.setText(time);
        Button btnGomaps = dialog.findViewById(R.id.btnGomaps);
        btnGomaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("".equals(stringLatitude) && "".equals(stringLongitude)) {
                    String uri = String.format(Locale.ENGLISH, "geo:%s,%s?q=%s,%s(" + code + ")", strLat, strLong, strLat, strLong);
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=" + stringLatitude + "," +
                            stringLongitude + "&daddr=" + strLat + "," + strLong + ""));
                    intent.setPackage("com.google.android.apps.maps");
                    startActivity(intent);
                }
            }
        });

        dialog.show();
    }

    public static Bitmap createCustomMarker(Context context, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.show_title, null);

        TextView txt_name = (TextView) marker.findViewById(R.id.tvTitle);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

}
