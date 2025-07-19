package com.asif.stepupbd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

import java.util.ArrayList;

public class EmployeePage extends AppCompatActivity {

    Button btnViewJobs, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        btnViewJobs = findViewById(R.id.btnViewJobs);
        btnLogout = findViewById(R.id.btnLogout);

        btnViewJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EmployeePage.this, ViewJobs.class);
                startActivity(intent);
            }
        });


        btnLogout.setOnClickListener(v -> {
            SharedPreferences sp = getSharedPreferences("myApp", MODE_PRIVATE);
            sp.edit().clear().apply();
            Intent intent = new Intent(EmployeePage.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
