package com.asif.stepupbd;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PostJobActivity extends AppCompatActivity {

    EditText etDescription, etSkill, etExperience, etDuration;
    Button btnPost;
    String URL = "http://192.168.1.101/apps/create_job.php"; // Replace with your server IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        etDescription = findViewById(R.id.etDescription);
        etSkill = findViewById(R.id.etSkill);
        etExperience = findViewById(R.id.etExperience);
        etDuration = findViewById(R.id.etDuration);
        btnPost = findViewById(R.id.btnPost);

        btnPost.setOnClickListener(v -> {
            String description = etDescription.getText().toString().trim();
            String skill = etSkill.getText().toString().trim();
            String experience = etExperience.getText().toString().trim();
            String duration = etDuration.getText().toString().trim();

            if(description.isEmpty() || skill.isEmpty() || experience.isEmpty() || duration.isEmpty()) {
                Toast.makeText(PostJobActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sp = getSharedPreferences("myApp", MODE_PRIVATE);
            String postedBy = sp.getString("email", "");

            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("description", description);
                jsonBody.put("skill", skill);
                jsonBody.put("experience", experience);
                jsonBody.put("duration", duration);
                jsonBody.put("posted_by", postedBy);

                postJob(jsonBody);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });
    }

    private void postJob(JSONObject jsonBody) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Posting job...");
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                response -> {
                    dialog.dismiss();
                    try {
                        String status = response.getString("status");
                        if(status.equalsIgnoreCase("success")) {
                            Toast.makeText(PostJobActivity.this, "Job posted successfully", Toast.LENGTH_SHORT).show();
                            finish(); // Close activity or clear fields
                        } else {
                            Toast.makeText(PostJobActivity.this, "Post failed: " + response.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(PostJobActivity.this, "Response error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    dialog.dismiss();
                    Toast.makeText(PostJobActivity.this, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public java.util.Map<String, String> getHeaders() {
                java.util.Map<String, String> headers = new java.util.HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }
}
