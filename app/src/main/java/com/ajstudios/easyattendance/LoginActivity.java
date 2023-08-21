package com.ajstudios.easyattendance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText txtUserName, txtPassword;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnLogin = findViewById(R.id.btnLogin);

        txtUserName = findViewById(R.id.txtUserName);
        txtPassword = findViewById(R.id.txtPassword);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.btnLogin) {

            String email = txtUserName.getText().toString();
            String password = txtPassword.getText().toString();

            final Register user = realm.where(Register.class)
                    .equalTo("username", email)
                    .equalTo("password", password)
                    .findFirst();

            if (user != null) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Invalid UserName/Password", Toast.LENGTH_SHORT).show();
            }
        }
    }
}