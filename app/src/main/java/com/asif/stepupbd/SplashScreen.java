package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000; // 2 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen); // Make sure this file exists in res/layout

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            SharedPreferences prefs = getSharedPreferences("myApp", MODE_PRIVATE);
            String email = prefs.getString("email", "");
            String userType = prefs.getString("user_type", "");

            Intent intent;
            if (!email.isEmpty()) {
                switch (userType) {
                    case "employee":
                        intent = new Intent(SplashScreen.this, EmployeePage.class);
                        break;
                    case "employer":
                        intent = new Intent(SplashScreen.this, EmployerrPage.class);
                        break;
                    case "uddokta":
                        intent = new Intent(SplashScreen.this, UddoktaPage.class);
                        break;
                    default:
                        intent = new Intent(SplashScreen.this, LoginActivity.class);
                }
            } else {
                intent = new Intent(SplashScreen.this, LoginActivity.class);
            }

            startActivity(intent);
            finish();
        }, SPLASH_DELAY);
    }
}
