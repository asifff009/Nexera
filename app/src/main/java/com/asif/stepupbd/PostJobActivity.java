package com.asif.stepupbd;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PostJobActivity extends AppCompatActivity {

    EditText descriptionEt, skillEt, experienceEt, durationEt, imageUrlEt;
    Button postJobBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_job);

        descriptionEt = findViewById(R.id.descriptionEt);
        skillEt = findViewById(R.id.skillEt);
        experienceEt = findViewById(R.id.experienceEt);
        durationEt = findViewById(R.id.durationEt);
        imageUrlEt = findViewById(R.id.imageUrlEt);
        postJobBtn = findViewById(R.id.postJobBtn);

        postJobBtn.setOnClickListener(v -> postJob());
    }

    private void postJob() {
        String description = descriptionEt.getText().toString().trim();
        String skill = skillEt.getText().toString().trim();
        String experience = experienceEt.getText().toString().trim();
        String duration = durationEt.getText().toString().trim();
        String imageUrl = imageUrlEt.getText().toString().trim();

        if (TextUtils.isEmpty(description) || TextUtils.isEmpty(skill) || TextUtils.isEmpty(experience) || TextUtils.isEmpty(duration)) {
            Toast.makeText(this, "Please fill all fields except image URL", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting job...");
        progressDialog.show();

        String url = "http://192.168.1.102/apps/post_job.php";

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> {
                    progressDialog.dismiss();
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String status = jsonObject.getString("status");
                        String message = jsonObject.getString("message");

                        Toast.makeText(PostJobActivity.this, message, Toast.LENGTH_LONG).show();

                        if (status.equals("success")) {
                            clearFields();
                        }
                    } catch (Exception e) {
                        Toast.makeText(PostJobActivity.this, "Unexpected response from server", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(PostJobActivity.this, "Failed to post job: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("description", description);
                params.put("skill", skill);
                params.put("experience", experience);
                params.put("duration", duration);
                params.put("image", imageUrl); // Optional: can be empty
                return params;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    private void clearFields() {
        descriptionEt.setText("");
        skillEt.setText("");
        experienceEt.setText("");
        durationEt.setText("");
        imageUrlEt.setText("");
    }
}
