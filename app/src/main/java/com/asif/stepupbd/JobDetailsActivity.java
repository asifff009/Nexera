package com.asif.stepupbd;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class JobDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        TextView title = findViewById(R.id.textTitle);
        TextView desc = findViewById(R.id.textDescription);
        TextView exp = findViewById(R.id.textExperience);
        TextView dur = findViewById(R.id.textDuration);
        TextView loc = findViewById(R.id.textLocation);
        TextView contact = findViewById(R.id.textContact);

        title.setText("Title: " + getIntent().getStringExtra("title"));
        desc.setText("Description: " + getIntent().getStringExtra("description"));
        exp.setText("Experience: " + getIntent().getStringExtra("experience"));
        dur.setText("Duration: " + getIntent().getStringExtra("duration"));
        loc.setText("Location: " + getIntent().getStringExtra("location"));
        contact.setText("Contact: " + getIntent().getStringExtra("contact"));
    }
}
