package com.asif.stepupbd;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApplyDialog extends Dialog {

    private final int jobId;

    public ApplyDialog(Context context, int jobId) {
        super(context);
        this.jobId = jobId;
    }

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.dialog_apply);

        EditText skillInput = findViewById(R.id.skillInput);
        EditText experienceInput = findViewById(R.id.experienceInput);
        EditText interestInput = findViewById(R.id.interestInput);
        Button submitBtn = findViewById(R.id.submitApplicationBtn);

        submitBtn.setOnClickListener(v -> {
            String skill = skillInput.getText().toString().trim();
            String experience = experienceInput.getText().toString().trim();
            String interest = interestInput.getText().toString().trim();

            if (skill.isEmpty() || experience.isEmpty() || interest.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences prefs = getContext()
                    .getSharedPreferences("myApp", Context.MODE_PRIVATE);
            String employerEmail = prefs.getString("email", "");
            if (employerEmail.isEmpty()) {
                Toast.makeText(getContext(), "Not logged in", Toast.LENGTH_SHORT).show();
                dismiss();
                return;
            }

            String url = "http://192.168.1.102/apps/apply_job.php";
            StringRequest req = new StringRequest(Request.Method.POST, url,
                    resp -> {
                        // Expected response: {"status":"success"} or {"status":"error"}
                        if (resp.contains("success")) {
                            Toast.makeText(getContext(), "Applied successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Failed to apply", Toast.LENGTH_SHORT).show();
                        }
                        dismiss();
                    },
                    err -> Toast.makeText(getContext(), "Error: " + err.getMessage(), Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> m = new HashMap<>();
                    m.put("job_id", String.valueOf(jobId));
                    m.put("employer_email", employerEmail);
                    m.put("skill", skill);
                    m.put("experience", experience);
                    m.put("interest", interest);
                    return m;
                }
            };

            Volley.newRequestQueue(getContext()).add(req);
        });
    }
}
