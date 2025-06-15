package com.asif.stepupbd;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ApplyJobHelper {

    public static void applyToJob(Context context, int jobId, String email, String skill, String experience, String interest) {
        String url = "http://192.168.1.102/apps/apply_job.php"; // 🔁 update if needed

        StringRequest request = new StringRequest(Request.Method.POST, url,
                response -> Toast.makeText(context, "Application submitted!", Toast.LENGTH_SHORT).show(),
                error -> Toast.makeText(context, "Failed to submit application", Toast.LENGTH_SHORT).show()) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("job_id", String.valueOf(jobId));
                params.put("employee_email", email);
                params.put("skill", skill);
                params.put("experience", experience);
                params.put("interest", interest);
                return params;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }
}
