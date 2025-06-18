package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class UddoktaPage extends AppCompatActivity {

    Button logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uddokta_page);

        TextView textView = findViewById(R.id.uddoktaText);
        logoutBtn = findViewById(R.id.logoutBtn);

        logoutBtn.setOnClickListener(v -> {
            SharedPreferences.Editor editor = getSharedPreferences("myApp", MODE_PRIVATE).edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(UddoktaPage.this, LoginActivity.class));
            finish();
        });
    }
}
