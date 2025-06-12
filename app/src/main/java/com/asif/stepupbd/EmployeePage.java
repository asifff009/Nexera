package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.asif.stepupbd.R;

public class EmployeePage extends AppCompatActivity {

    Button buttonNxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        buttonNxt = findViewById(R.id.buttonNxt);

        buttonNxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentt = new Intent(EmployeePage.this, Logout.class);
                startActivity(intentt);

            }
        });


    }
}
