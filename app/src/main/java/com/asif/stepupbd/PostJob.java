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

public class PostJob extends AppCompatActivity {

    Button buttonSeeJob, buttonPostJob;
    EditText edTitle, edDescription, edExperience, edDuration, edLocation, edContact;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        buttonSeeJob = findViewById(R.id.buttonSeeJob);
        buttonPostJob = findViewById(R.id.buttonPostJob);
        edDescription = findViewById(R.id.edDescription);
        edExperience = findViewById(R.id.edExperience);
        edDuration = findViewById(R.id.edDuration);
        edLocation = findViewById(R.id.edLocation);
        edContact = findViewById(R.id.edContact);
        edTitle = findViewById(R.id.edTitle);
        progressBar = findViewById(R.id.progressBar);






        buttonSeeJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iintent = new Intent(PostJob.this, SeeJobListPost.class);
                startActivity(iintent);
            }
        });

        buttonPostJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // String gula k niye nilam

                String title = edTitle.getText().toString();
                String description = edDescription.getText().toString();
                String experience = edExperience.getText().toString();
                String duration = edDuration.getText().toString();
                String location = edLocation.getText().toString();
                String contact = edContact.getText().toString();

                String url = "http://192.168.1.102/apps/post_job.php?a=" + title
                        + "&b=" + description + "&c=" + experience + "&d=" + duration + "&e=" + location + "&f=" + contact ;

                progressBar.setVisibility(View.VISIBLE);

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        new AlertDialog.Builder(PostJob.this)
                                .setTitle("Job Post")
                                .setMessage("Your Job is successfully posted")
                                .show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

                // RequesQueue toiri korlam
                RequestQueue requestQueue = Volley.newRequestQueue(PostJob.this);
                requestQueue.add(stringRequest);

            }
        });



    }
}