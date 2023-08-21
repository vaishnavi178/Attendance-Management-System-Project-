package com.ajstudios.easyattendance;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import io.realm.Realm;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etUser, etPass;
    private Realm realm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        Button button = findViewById(R.id.btnLogin);
        Button button2 = findViewById(R.id.btnRegister);

        etPass = findViewById(R.id.etPassword);
        etUser = findViewById(R.id.etUsername);

        Realm.init(getApplicationContext());
        realm = Realm.getDefaultInstance();
        button.setOnClickListener(this);
        button2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btnLogin) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.btnRegister) {
            if (etUser.getText().toString().trim().length() == 0) {
                etUser.setError("Enter username");
                etUser.requestFocus();
            } else if (etPass.getText().toString().trim().length() == 0) {
                etPass.setError("Enter password");
                etPass.requestFocus();
            } else {

                String email = etUser.getText().toString();
                String password = etPass.getText().toString();

                final Register user = realm.where(Register.class)
                        .equalTo("username", email)
                        .equalTo("password", password)
                        .findFirst();

                if (user != null) {
                    Toast.makeText(this, "User Already Registered", Toast.LENGTH_SHORT).show();
                } else {

                    final Register mUser = new Register(email, password);
                    // on below line we are calling a method to execute a transaction.
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(@NonNull Realm realm) {
                            assert false;
                            realm.copyToRealm(mUser);
                            Toast.makeText(getApplicationContext(), "User Registered", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        }


    }
}