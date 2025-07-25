package com.asif.stepupbd;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ApplyDetails extends AppCompatActivity {

    Button buttonApplyDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_details);

        buttonApplyDetails = findViewById(R.id.buttonApplyDetails);

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

        buttonApplyDetails.setOnClickListener(view -> showCallDialog());
    }

    private void showCallDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Call Applicant");
        builder.setMessage("Do you want to call this applicant?");

        builder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                callApplicant();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void callApplicant() {
        String phone = getIntent().getStringExtra("contact");
        if (phone != null && !phone.isEmpty()) {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phone));
            startActivity(intent);
        } else {
            // Optionally, show a message if no phone number is available
            new AlertDialog.Builder(this)
                    .setMessage("Phone number not available.")
                    .setPositiveButton("OK", null)
                    .show();
        }
    }
}
