package com.example.projectpas;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class SignUpPage extends AppCompatActivity {

    public static final String SHARED_PREFS = "shared_prefs";
    public static final String EMAIL_KEY = "email_key";
    public static final String USERNAME_KEY = "username_key";
    public static final String PASSWORD_KEY = "password_key";

    SharedPreferences sharedpreferences;

    TextView tvLogin;
    EditText etEmail, etUsername2, etPassword2;
    ProgressBar LoadingBar;
    Button btnSignUp;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        LoadingBar = findViewById(R.id.LoadingBar);
        LoadingBar.setVisibility(View.GONE);

        tvLogin = findViewById(R.id.tvLogin);
        etEmail = findViewById(R.id.etEmail);
        etUsername2 = findViewById(R.id.etUsername2);
        etPassword2 = findViewById(R.id.etPassword2);

        btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = etEmail.getText().toString();
                String username = etUsername2.getText().toString();
                String password = etPassword2.getText().toString();
                LoadingBar.setVisibility(View.VISIBLE);
                btnSignUp.setEnabled(false);

                AndroidNetworking.post("https://mediadwi.com/api/latihan/register-user")
                        .addBodyParameter("username", username)
                        .addBodyParameter("password", password)
                        .addBodyParameter("email", email)
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                // Handle successful response
                                Log.d("sukses login", "onResponse: "+response.toString());
                                try {
                                    boolean status = response.getBoolean("status");
                                    String message = response.getString("message");
                                    Toast.makeText(SignUpPage.this, message, Toast.LENGTH_SHORT).show();
                                    if (status){
                                        // atau silahkan buat dialog
                                        sharedpreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putString(EMAIL_KEY, "inas.tsabitah22@gmail.com");
                                        editor.putString(USERNAME_KEY, "tsabitah");
                                        editor.putString(PASSWORD_KEY, "password");

                                        // to save our data with key and value.
                                        editor.apply();
                                        Intent login = new Intent(SignUpPage.this, MainActivity.class);
                                        startActivity(login);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LoadingBar.setVisibility(View.GONE);
                                btnSignUp.setEnabled(true);
                            }
                            @Override
                            public void onError(ANError error) {
                                // Handle error
                                Toast.makeText(SignUpPage.this, "Failed Login", Toast.LENGTH_SHORT).show();

                                LoadingBar.setVisibility(View.GONE);
                                btnSignUp.setEnabled(true);
                            }
                        });
            }
        });

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(SignUpPage.this, LoginPage.class);
                startActivity(login);
            }
        });
    }
}