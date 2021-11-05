package com.year3project.qrcodescanner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_OBJECT = "LogDataObject";

    CodeScanner codeScanner;
    CodeScannerView scannerView;
    TextView resultData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scannerView = findViewById(R.id.scannerView);
        codeScanner = new CodeScanner(this, scannerView);
        resultData = findViewById(R.id.resultsOfQR);

        codeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        LogData logData = formatLogData(result.toString());
                        if (logData.validQRData) {
                            openSaveRecord(logData);
                        }
                    }
                });
            }
        });

        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                codeScanner.startPreview();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestForCamera();
    }

    private void openSaveRecord(LogData logData) {
        Intent intent = new Intent(this, SaveRecordData.class);
        intent.putExtra(EXTRA_OBJECT, logData);
        startActivity(intent);
    }

    private void requestForCamera() {
        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                codeScanner.startPreview();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Camera permission is required", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }

    private LogData formatLogData(String logData) {
        String[] logDataArray = logData.split(",,,,");
        int count = 0;

        LogData logData1 = new LogData();

        if (logDataArray.length != 7) {
            logData1.validQRData = false;
            return logData1;
        }

        for (String data : logDataArray) {
            if (count == 0) {
                logData1.fullName = data;
            } else if (count == 1) {
                logData1.contactNumber = data;
            } else if (count == 2) {
                logData1.address = data;
            } else if (count == 3) {
                logData1.age = data;
            } else if (count == 4) {
                logData1.temperature = data;
            } else if (count == 5) {
                logData1.gender = data;
            } else if (count == 6) {
                logData1.reason = data;
            }

            count++;
        }

        logData1.validQRData = true;
        return logData1;
    }
}