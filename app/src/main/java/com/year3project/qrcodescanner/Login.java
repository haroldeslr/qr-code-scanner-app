package com.year3project.qrcodescanner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    private AlertDialog alertDialog;

    EditText usernameTV;
    EditText passwordTV;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();

        usernameTV = findViewById(R.id.tf_username);
        passwordTV = findViewById(R.id.tf_password);
        loginButton = findViewById(R.id.login_button);
        alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Failed");
    }

    public void onLogin(View view) {
        loginButton.setEnabled(false);
        String usernameValue = usernameTV.getText().toString();
        String passwordValue = passwordTV.getText().toString();

        if (usernameValue.isEmpty() || passwordValue.isEmpty()) {
            alertDialog.setMessage("Fill up form");
            alertDialog.show();
            loginButton.setEnabled(true);
        } else {
            LoginUser loginUser = new LoginUser(this, usernameValue, passwordValue, loginButton);
            loginUser.execute();
        }
    }
}