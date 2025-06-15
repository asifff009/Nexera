package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EmployerrPage extends AppCompatActivity {

    Button postJobBtn, viewApplicationsBtn, logoutBtn, viewMyJobsBtn;

    int jobId = 5; // This can be dynamically set as needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerr_page);

        postJobBtn = findViewById(R.id.postJobBtn);
        viewApplicationsBtn = findViewById(R.id.viewApplicationsBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        viewMyJobsBtn = findViewById(R.id.viewMyJobsBtn); // Added button

        // 🔸 View Applications Button
        viewApplicationsBtn.setOnClickListener(v -> {
            if (jobId == -1) {
                Toast.makeText(this, "Invalid Job ID", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(EmployerrPage.this, ViewApplicationsActivity.class);
            intent.putExtra("job_id", jobId);
            startActivity(intent);
        });

        // 🔸 Post Job Button
        postJobBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerrPage.this, PostJobActivity.class);
            startActivity(intent);
        });

        // 🔸 View My Jobs Button
        viewMyJobsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerrPage.this, EmployerJobsActivity.class);
            startActivity(intent);
        });

        // 🔸 Logout Button
        logoutBtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            Intent intent = new Intent(EmployerrPage.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
