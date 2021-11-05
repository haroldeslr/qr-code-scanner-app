package com.year3project.qrcodescanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SaveRecordData extends AppCompatActivity {
    public static final String EXTRA_OBJECT = "LogDataObject";

    private EditText fullName;
    private EditText contactNumber;
    private EditText address;
    private EditText age;
    private EditText temperature;
    private EditText gender;
    private EditText reason;
    private Button uploadLog;

    LogData logData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_record_data);

        Intent intent = getIntent();
        logData = (LogData) intent.getSerializableExtra(EXTRA_OBJECT);

        fullName = findViewById(R.id.full_name);
        contactNumber = findViewById(R.id.contact_number);
        address = findViewById(R.id.address);
        age = findViewById(R.id.age);
        temperature = findViewById(R.id.temperature);
        gender = findViewById(R.id.gender);
        reason = findViewById(R.id.reason);
        uploadLog = findViewById(R.id.upload_log);

        fullName.setText(logData.fullName);
        contactNumber.setText(logData.contactNumber);
        address.setText(logData.address);
        age.setText(logData.age);
        temperature.setText(logData.temperature);
        gender.setText(logData.gender);
        reason.setText(logData.reason);
    }

    public void onUpload(View view) {
        uploadLog.setEnabled(false);

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

        BackgroundWorker backgroundWorker = new BackgroundWorker(this, logData);
        backgroundWorker.execute();
    }
}