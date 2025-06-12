package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.asif.stepupbd.R;

public class UddoktaPage extends AppCompatActivity {

    Button buttonNxt3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uddokta_page);

        buttonNxt3 = findViewById(R.id.buttonNxt3);

        buttonNxt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenttt = new Intent(UddoktaPage.this, Logout.class);
                startActivity(intenttt);

            }
        });



    }
}
