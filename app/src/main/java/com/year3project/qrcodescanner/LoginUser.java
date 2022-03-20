package com.year3project.qrcodescanner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

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

public class LoginUser extends AsyncTask<String, Void, String> {

    private final static String TAG = "LoginUser";

    public static final String userDataPreferences = "UserDataPreferences";
    public static final String usernameKey = "UsernameKey";
    public static final String passwordKey = "PasswordKey";
    public static final String isUserLoginKey = "IsUserLoginKey";

    Context context;
    String username;
    String password;

    Button loginButton;
    AlertDialog alertDialog;

    public LoginUser(Context context, String username, String password, Button loginButton) {
        this.context = context;
        this.username = username;
        this.password = password;
        this.loginButton = loginButton;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Login Failed");
    }

    @Override
    protected String doInBackground(String... strings) {
        String uploadURL = "https://upang-cls.000webhostapp.com/php/login_scanner_app.php";
        try {
            URL url = new URL(uploadURL);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("username", "UTF-8")+"="+URLEncoder.encode(username, "UTF-8")+"&"+
                    URLEncoder.encode("password", "UTF-8")+"="+URLEncoder.encode(password, "UTF-8");
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
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result.equals("Login Success")) {
            saveUserData();
            navigateToScannerActivity();
        } else {
            alertDialog.setMessage(result);
            alertDialog.show();
            loginButton.setEnabled(true);
        }
    }

    private void saveUserData() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(userDataPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editUserData = sharedPreferences.edit();
        editUserData.putString(usernameKey, username);
        editUserData.putString(passwordKey, password);
        editUserData.putBoolean(isUserLoginKey, true);
        editUserData.commit();
    }

    private void navigateToScannerActivity() {
        Intent intent = new Intent(context, ScannerActivity.class);
        context.startActivity(intent);
    }
}
