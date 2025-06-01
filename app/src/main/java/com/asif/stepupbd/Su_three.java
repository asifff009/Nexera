package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Su_three extends AppCompatActivity {

    TextView buttonEmployee, buttonEmployer, buttonUddoktaCorner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_three);

        buttonEmployee = findViewById(R.id.buttonEmployee);
        buttonEmployer = findViewById(R.id.buttonEmployer);
        buttonUddoktaCorner = findViewById(R.id.buttonUddoktaCorner);


        buttonEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Su_three.this, Signup.class));
            }
        });


    }

}