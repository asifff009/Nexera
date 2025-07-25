package com.asif.stepupbd;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ApplyDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_details);

        TextView name = findViewById(R.id.textName);
        TextView inte = findViewById(R.id.textInterest);
        TextView exp = findViewById(R.id.textExperience);
        TextView skill = findViewById(R.id.textSkill);
        TextView loc = findViewById(R.id.textLocation);
        TextView contact = findViewById(R.id.textContact);

        name.setText("Name: " + getIntent().getStringExtra("name"));
        inte.setText("Interest: " + getIntent().getStringExtra("interest"));
        exp.setText("Experience: " + getIntent().getStringExtra("experience"));
        skill.setText("Skill: " + getIntent().getStringExtra("skill"));
        loc.setText("Location: " + getIntent().getStringExtra("location"));
        contact.setText("Contact: " + getIntent().getStringExtra("contact"));

    }
}