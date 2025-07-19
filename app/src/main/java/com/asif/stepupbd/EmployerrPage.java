package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class EmployerrPage extends AppCompatActivity {

    Button postJobBtn, logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerr_page);

        postJobBtn = findViewById(R.id.postJobBtn);
        logoutBtn = findViewById(R.id.logoutBtn);

        postJobBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployerrPage.this, PostJob.class);
                startActivity(intent);
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
