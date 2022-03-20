package com.year3project.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveRecordDataActivity extends AppCompatActivity {
    public static final String EXTRA_OBJECT = "LogDataObject";

    private TextView fullName;
    private TextView contactNumber;
    private TextView address;
    private TextView age;
    private TextView temperature;
    private TextView gender;
    private TextView reason;
    private Button uploadLog;

    LogData logData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_record_data);

        Intent intent = getIntent();
        logData = (LogData) intent.getSerializableExtra(EXTRA_OBJECT);

        fullName = findViewById(R.id.tv_fullname);
        contactNumber = findViewById(R.id.tv_contact);
        address = findViewById(R.id.tv_address);
        age = findViewById(R.id.tv_age);
        temperature = findViewById(R.id.tv_temp);
        gender = findViewById(R.id.tv_gender);
        reason = findViewById(R.id.tv_purpose);
        uploadLog = findViewById(R.id.upload_button);

        fullName.setText(logData.fullName);
        contactNumber.setText(logData.contactNumber);
        address.setText(logData.address);
        age.setText(logData.age);
        temperature.setText(logData.temperature);
        gender.setText(logData.gender);
        reason.setText(logData.reason);
    }

    public void onUpload(View view) {
        this.uploadLog.setEnabled(false);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateAndTime = sdf.format(new Date());

        logData.fullName = fullName.getText().toString();
        logData.contactNumber = contactNumber.getText().toString();
        logData.address = address.getText().toString();
        logData.age = age.getText().toString();
        logData.temperature = temperature.getText().toString();
        logData.gender = gender.getText().toString();
        logData.reason = reason.getText().toString();
        logData.dateAndTime = currentDateAndTime;

        UploadLog uploadLog = new UploadLog(this, logData);
        uploadLog.execute();
    }
}