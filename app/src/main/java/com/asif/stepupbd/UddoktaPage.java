package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

public class UddoktaPage extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    HorizontalScrollView horizontalScrollView;
    GridView gridView;
    Handler handler = new Handler();

    int currentIndex = 0;
    int imageWidth = 290; // image width + margin (approx)

    String[] services = {
            "Pet sitting",
            "T-shirt printing",
            "Online teaching",
            "Online bookkeeping",
            "Consulting",
            "Medical courier service",
            "App development",
            "Transcription service",
            "Professional organizing"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uddokta_page);

        // Login check
        sharedPreferences = getSharedPreferences("myApp", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "");

        if (email.isEmpty()) {
            startActivity(new Intent(UddoktaPage.this, Login.class));
            finish();
            return;
        }

        // Initialize scroll view and grid view
        horizontalScrollView = findViewById(R.id.horizontalScrollView);
        gridView = findViewById(R.id.gridView);

        // GridView adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                services
        );
        gridView.setAdapter(adapter);

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
        }, 3000); // Start after 3 seconds
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
