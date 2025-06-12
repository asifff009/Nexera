package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.asif.stepupbd.R;

public class EmployerrPage extends AppCompatActivity {

    Button buttonNxt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employerr_page);

        buttonNxt2 = findViewById(R.id.buttonNxt2);

        buttonNxt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttt = new Intent(EmployerrPage.this, Logout.class);
                startActivity(intenttt);

            }
        });




    }
}
