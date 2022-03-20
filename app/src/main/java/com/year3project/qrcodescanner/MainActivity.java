package com.year3project.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private int splashTimer = 3;

    private final String userDataPreferences = "UserDataPreferences";
    private final String isUserLoginKey = "IsUserLoginKey";

    private Boolean isUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        SharedPreferences sharedPreferences = getSharedPreferences(userDataPreferences, MODE_PRIVATE);
        isUserLogin = sharedPreferences.getBoolean(isUserLoginKey, false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isUserLogin) {
                    navigateToScannerActivity();
                } else {
                    navigateToLoginActivity();
                }
            }
        }, splashTimer * 1000);
    }

    private void navigateToLoginActivity() {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void navigateToScannerActivity() {
        Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
        startActivity(intent);
        finish();
    }
}