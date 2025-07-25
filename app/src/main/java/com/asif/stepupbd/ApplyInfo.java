package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
public class ApplyInfo extends AppCompatActivity {

    EditText edName, edInterest, edExperience, edSkill, edLocation, edContact;
    ProgressBar progressBar;
    Button buttonApplyJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_info);

        edName = findViewById(R.id.edName);
        edInterest = findViewById(R.id.edInterest);
        edExperience = findViewById(R.id.edExperience);
        edSkill = findViewById(R.id.edSkill);
        edLocation = findViewById(R.id.edLocation);
        edContact = findViewById(R.id.edContact);
        progressBar = findViewById(R.id.progressBar);
        buttonApplyJob = findViewById(R.id.buttonApplyJob);

        buttonApplyJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edName.getText().toString();
                String interest = edInterest.getText().toString();
                String experience = edExperience.getText().toString();
                String skill = edSkill.getText().toString();
                String location = edLocation.getText().toString();
                String contact = edContact.getText().toString();

                String url = "http://192.168.1.102/apps/apply_job.php?a=" + name
                        + "&b=" + interest + "&c=" + experience + "&d=" + skill + "&e=" + location + "&f=" + contact ;


                progressBar.setVisibility(View.VISIBLE);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        new AlertDialog.Builder(ApplyInfo.this)
                                .setTitle("Job Apply")
                                .setMessage("Your apply in this job is successful")
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                // RequesQueue toiri korlam
                RequestQueue requestQueue = Volley.newRequestQueue(ApplyInfo.this);
                requestQueue.add(stringRequest);

            }
        });


    }
}