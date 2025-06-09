package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DELAY = 1500; // 1.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
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
            finish(); // Important to close splash screen
        }, SPLASH_DELAY);
    }
}
