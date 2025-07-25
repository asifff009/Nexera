package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EmployerrPage extends AppCompatActivity {

    Button postJobBtn, logoutBtn,buttonSeeJob, buttonSeeApplicant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerr_page);

        postJobBtn = findViewById(R.id.postJobBtn);
        logoutBtn = findViewById(R.id.logoutBtn);
        buttonSeeJob = findViewById(R.id.buttonSeeJob);
        buttonSeeApplicant = findViewById(R.id.buttonSeeApplicant);

        buttonSeeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iintent = new Intent(EmployerrPage.this, SeeJobListPost.class);
                startActivity(iintent);
            }
        });

        postJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployerrPage.this, PostJob.class);
                startActivity(intent);
            }
        });

        buttonSeeApplicant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aintent = new Intent(EmployerrPage.this, SeeApplyList.class);
                startActivity(aintent);
            }
        });






        logoutBtn.setOnClickListener(v -> {
            SharedPreferences preferences = getSharedPreferences("myApp", MODE_PRIVATE);
            preferences.edit().clear().apply();

            Intent intent = new Intent(EmployerrPage.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }
}
