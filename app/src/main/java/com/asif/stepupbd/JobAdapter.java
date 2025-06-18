package com.asif.stepupbd;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    Context context;
    ArrayList<JobModel> jobList;

    public JobAdapter(Context context, ArrayList<JobModel> jobList) {
        this.context = context;
        this.jobList = jobList;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_job, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobModel job = jobList.get(position);
        holder.tvDescription.setText(job.description);
        holder.tvSkill.setText("Skill: " + job.skill);
        holder.tvExperience.setText("Experience: " + job.experience);
        holder.tvDuration.setText("Duration: " + job.duration);
        holder.tvPostedBy.setText("Posted by: " + job.postedBy);

        holder.btnApply.setOnClickListener(v -> showApplyDialog(job.id));
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    private void showApplyDialog(int jobId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_apply, null);
        builder.setView(view);

        EditText etSkill = view.findViewById(R.id.etApplySkill);
        EditText etExperience = view.findViewById(R.id.etApplyExperience);
        EditText etInterest = view.findViewById(R.id.etApplyInterest);
        Button btnSubmit = view.findViewById(R.id.btnSubmitApply);

        AlertDialog dialog = builder.create();

        btnSubmit.setOnClickListener(v -> {
            String skill = etSkill.getText().toString().trim();
            String experience = etExperience.getText().toString().trim();
            String interest = etInterest.getText().toString().trim();

            if (skill.isEmpty() || experience.isEmpty() || interest.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences sp = context.getSharedPreferences("myApp", Context.MODE_PRIVATE);
            String employeeEmail = sp.getString("email", "");

            JSONObject jsonBody = new JSONObject();
            try {
                jsonBody.put("job_id", jobId);
                jsonBody.put("employee_email", employeeEmail);
                jsonBody.put("skill", skill);
                jsonBody.put("experience", experience);
                jsonBody.put("interest", interest);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            submitApplication(jsonBody, dialog);
        });

        dialog.show();
    }

    private void submitApplication(JSONObject jsonBody, AlertDialog dialog) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Submitting application...");
        progressDialog.show();

        String URL = "http://192.168.1.101/apps/apply_job.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL, jsonBody,
                response -> {
                    progressDialog.dismiss();
                    try {
                        String status = response.getString("status");
                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(context, "Application submitted", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        } else {
                            Toast.makeText(context, "Failed: " + response.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Response parse error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressDialog.dismiss();
                    Toast.makeText(context, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };

        Volley.newRequestQueue(context).add(request);
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription, tvSkill, tvExperience, tvDuration, tvPostedBy;
        Button btnApply;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            tvSkill = itemView.findViewById(R.id.tvSkill);
            tvExperience = itemView.findViewById(R.id.tvExperience);
            tvDuration = itemView.findViewById(R.id.tvDuration);
            tvPostedBy = itemView.findViewById(R.id.tvPostedBy);
            btnApply = itemView.findViewById(R.id.btnApply);
        }
    }
}
