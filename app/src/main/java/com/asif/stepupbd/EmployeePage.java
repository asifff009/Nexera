package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.HorizontalScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class EmployeePage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    HorizontalScrollView horizontalScrollView;
    Handler handler = new Handler();

    int currentIndex = 0;
    int imageWidth = 290; // image width + margin (approx)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        // Login check
        sharedPreferences = getSharedPreferences("myApp", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        if (email.isEmpty()) {
            startActivity(new Intent(EmployeePage.this, Login.class));
            finish();
            return;
        }

        // Initialize scroll view
        horizontalScrollView = findViewById(R.id.horizontalScrollView);

        // Start the image sequence scroll
        startImageSequence();
    }

    private void startImageSequence() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int scrollX = currentIndex * imageWidth;
                horizontalScrollView.smoothScrollTo(scrollX, 0);

                currentIndex++;
                if (currentIndex >= 4) { // total 4 images
                    currentIndex = 0;
                }

                handler.postDelayed(this, 4000); // 4 seconds delay
            }
        }, 3000); // Start after 1 second
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
