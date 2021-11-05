package com.year3project.qrcodescanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BackgroundWorker extends AsyncTask<LogData, Void, String> {
    AlertDialog alertDialog;
    Context context;
    LogData logData;

    BackgroundWorker (Context context, LogData logData) {
        this.context = context;
        this.logData = logData;
    }

    @Override
    protected String doInBackground(LogData... params) {
        String fullName = logData.fullName;
        String contactNumber = logData.contactNumber;
        String address = logData.address;
        String age = logData.age;
        String temperature = logData.temperature;
        String gender = logData.gender;
        String reason = logData.reason;
        String time = logData.dateAndTime;

        String uploadURL = "https://upang-qrcode-generator-test3.000webhostapp.com/sql/upload.php";
        try {
            URL url = new URL(uploadURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("full_name", "UTF-8")+"="+URLEncoder.encode(fullName, "UTF-8")+"&"+
                    URLEncoder.encode("contact_number", "UTF-8")+"="+URLEncoder.encode(contactNumber, "UTF-8")+"&"+
                    URLEncoder.encode("address", "UTF-8")+"="+URLEncoder.encode(address, "UTF-8")+"&"+
                    URLEncoder.encode("age", "UTF-8")+"="+URLEncoder.encode(age, "UTF-8")+"&"+
                    URLEncoder.encode("temperature", "UTF-8")+"="+URLEncoder.encode(temperature, "UTF-8")+"&"+
                    URLEncoder.encode("gender", "UTF-8")+"="+URLEncoder.encode(gender, "UTF-8")+"&"+
                    URLEncoder.encode("reason", "UTF-8")+"="+URLEncoder.encode(reason, "UTF-8")+"&"+
                    URLEncoder.encode("time", "UTF-8")+"="+URLEncoder.encode(time, "UTF-8");
            bufferedWriter.write(post_data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();

            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
            String result = "";
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Upload Status");
    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                // TODO: Your application init goes here.
                Intent mInHome = new Intent(context, MainActivity.class);
                context.startActivity(mInHome);
            }
        }, 3000);
    }
}
