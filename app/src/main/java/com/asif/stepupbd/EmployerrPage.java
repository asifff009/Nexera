package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class EmployerrPage extends AppCompatActivity {

    Button postJobBtn, logoutBtn, buttonSeeJob, buttonSeeApplicant;
    HorizontalScrollView scrollView;
    LinearLayout imageContainer;

    Handler handler = new Handler();
    int currentIndex = 0;
    int delayMillis = 2000;
    int imageWidthDp = 260; // matches XML layout
    Runnable scrollRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerr_page);

        // Initialize views
        postJobBtn = findViewById(R.id.postJobBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        buttonSeeJob = findViewById(R.id.buttonSeeJob);
        buttonSeeApplicant = findViewById(R.id.buttonSeeApplicant);
        scrollView = findViewById(R.id.scrollView);
        imageContainer = findViewById(R.id.imageContainer);

        // Auto-scroll images
        startAutoScroll();

        // Button Listeners
        postJobBtn.setOnClickListener(v ->
                startActivity(new Intent(EmployerrPage.this, PostJob.class)));

        buttonSeeJob.setOnClickListener(v ->
                startActivity(new Intent(EmployerrPage.this, SeeJobListPost.class)));

        buttonSeeApplicant.setOnClickListener(v ->
                startActivity(new Intent(EmployerrPage.this, SeeApplyList.class)));

        logoutBtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("myApp", MODE_PRIVATE);
            preferences.edit().clear().apply();
            Intent intent = new Intent(EmployerrPage.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void startAutoScroll() {
        scrollRunnable = new Runnable() {
            @Override
            public void run() {
                int scrollToX = currentIndex * dpToPx(imageWidthDp + 10); // 260dp + 10dp margin
                scrollView.smoothScrollTo(scrollToX, 0);

                currentIndex++;
                if (currentIndex >= imageContainer.getChildCount()) {
                    currentIndex = 0;
                }

                handler.postDelayed(this, delayMillis);
            }
        };

        handler.postDelayed(scrollRunnable, delayMillis);
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(scrollRunnable); // stop when activity is destroyed
    }
}
