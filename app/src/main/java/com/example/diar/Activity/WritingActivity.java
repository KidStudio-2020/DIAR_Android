package com.example.diar.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.diar.Database.DBHelper;
import com.example.diar.Database.LetterDB;
import com.example.diar.Gps.GpsTracker;
import com.example.diar.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WritingActivity extends AppCompatActivity {

    private EditText month;
    private EditText date;
    private EditText day;
    private EditText weather;
    private EditText word;
    private EditText title_test;
    private EditText content_test;
    private DBHelper helper;
    private SQLiteDatabase database;
    private Button writeBtn;
    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);

        helper = new DBHelper(WritingActivity.this, "letter", null, 1);
        database = helper.getWritableDatabase();
        Button ShowLocationButton = (Button) findViewById(R.id.location);
        Button check = findViewById(R.id.check);
        month = findViewById(R.id.etMonth);
        date = findViewById(R.id.etDate);
        day = findViewById(R.id.etDay);
        weather = findViewById(R.id.etWeather);
        word = findViewById(R.id.etWord);
        title_test = findViewById(R.id.etTitle);
        content_test = findViewById(R.id.etContent);

        writeBtn = (Button) findViewById(R.id.writeBtn);
        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        } else {

            checkRunTimePermission();
        }

        writeBtn.setOnClickListener(v -> {
            String title = title_test.toString();
            String create_date = month.getText().toString() + " 월 " + day.getText().toString() + "일";
            String content = content_test.getText().toString();
            Double lat = 0.0;
            Double lng = 0.0;
            String path = "test";
            if (title.equals("")) {
                Toast.makeText(WritingActivity.this, "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
            } else {
                helper.insertLetter(lat, lng, create_date, content, path, title);
                Intent intent = new Intent(WritingActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        check.setOnClickListener(v -> {
            StringBuffer resultAll = new StringBuffer();
            ArrayList < LetterDB > letterDBArrayList = helper.getAllLetter();
            for (int i = 0; i < letterDBArrayList.size(); i++) {
                resultAll.append(letterDBArrayList.get(i).toString());
            }
            Toast.makeText(WritingActivity.this, resultAll.toString(), Toast.LENGTH_LONG).show();
        });
        ShowLocationButton.setOnClickListener(arg0 -> {

                gpsTracker = new GpsTracker(WritingActivity.this);

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();
        String address = getCurrentAddress(latitude, longitude);
        Toast.makeText(WritingActivity.this, "lat: " + latitude + "\nlng: " + longitude + "\nadress: " + address, Toast.LENGTH_LONG).show();
        });


    }
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if (permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {
            boolean check_result = true;

            for (int result: grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }

            if (check_result) {} else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[0]) ||
                        ActivityCompat.shouldShowRequestPermissionRationale(this, REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(WritingActivity.this, "권한이 거부되었습니다. 앱을 다시 실행하여 권한을 허용해주세요.", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(WritingActivity.this, "권한이 거부되었습니다. 설정(앱 정보)에서 권한을 허용해주세요. ", Toast.LENGTH_LONG).show();
                }
            }

        }
    }

    void checkRunTimePermission() {
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(WritingActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(WritingActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(WritingActivity.this, REQUIRED_PERMISSIONS[0])) {
                Toast.makeText(WritingActivity.this, "DIAR을 실행하려면 위치 접근 권한이 필요니다.", Toast.LENGTH_LONG).show();
                ActivityCompat.requestPermissions(WritingActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);

            } else {
                ActivityCompat.requestPermissions(WritingActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }
    }

    public String getCurrentAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List < Address > addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";
        }
        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(this, "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }
        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";
    }
    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(WritingActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("DIAR을 사용하기 위해서는 위치 서비스가 필요합니다.\n" +
                "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", (dialog, id) -> {
                Intent callGPSSettingIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
        });
        builder.setNegativeButton("취소", (dialog, id) -> dialog.cancel());
        builder.create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case GPS_ENABLE_REQUEST_CODE:
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }
                break;
        }
    }
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}