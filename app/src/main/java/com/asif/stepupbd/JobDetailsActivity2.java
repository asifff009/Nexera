package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class JobDetailsActivity2 extends AppCompatActivity {

    Button buttonApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details2);

        TextView title = findViewById(R.id.textTitle);
        TextView desc = findViewById(R.id.textDescription);
        TextView exp = findViewById(R.id.textExperience);
        TextView dur = findViewById(R.id.textDuration);
        TextView loc = findViewById(R.id.textLocation);
        TextView contact = findViewById(R.id.textContact);

        buttonApply = findViewById(R.id.buttonApply);

        buttonApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent applyIntent = new Intent(JobDetailsActivity2.this, ApplyInfo.class);
                startActivity(applyIntent);
            }
        });

        title.setText("Title: " + getIntent().getStringExtra("title"));
        desc.setText("Description: " + getIntent().getStringExtra("description"));
        exp.setText("Experience: " + getIntent().getStringExtra("experience"));
        dur.setText("Duration: " + getIntent().getStringExtra("duration"));
        loc.setText("Location: " + getIntent().getStringExtra("location"));
        contact.setText("Contact: " + getIntent().getStringExtra("contact"));
    }
}
