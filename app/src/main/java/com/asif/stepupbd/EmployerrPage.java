package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class EmployerrPage extends AppCompatActivity {

    Button postJobBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerr_page);

        postJobBtn = findViewById(R.id.postJobBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        postJobBtn.setOnClickListener(v -> {
            Intent intent = new Intent(EmployerrPage.this, PostJobActivity.class);
            startActivity(intent);
        });

        logoutBtn.setOnClickListener(v -> {
            getSharedPreferences("myApp", MODE_PRIVATE).edit().clear().apply();
            Intent intent = new Intent(EmployerrPage.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
