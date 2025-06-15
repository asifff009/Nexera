package com.asif.stepupbd;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    Context context;
    List<JobModel> jobList;

    public JobAdapter(Context context, List<JobModel> jobList) {
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

        holder.description.setText(job.getDescription());
        holder.skill.setText("Skill: " + job.getSkill());
        holder.experience.setText("Experience: " + job.getExperience());
        holder.duration.setText("Duration: " + job.getDuration());

        Glide.with(context).load(job.getImage()).into(holder.imageView);

        holder.applyBtn.setOnClickListener(v -> {
            // ✅ Show dialog to apply
            showApplyDialog(job.getId());
        });
    }

    private void showApplyDialog(int jobId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_apply, null);

        EditText interestEt = view.findViewById(R.id.interestEditText);
        EditText skillEt = view.findViewById(R.id.skillEditText);
        EditText experienceEt = view.findViewById(R.id.experienceEditText);
        Button submitBtn = view.findViewById(R.id.submitApplyBtn);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        dialog.show();

        submitBtn.setOnClickListener(v -> {
            String interest = interestEt.getText().toString().trim();
            String skill = skillEt.getText().toString().trim();
            String experience = experienceEt.getText().toString().trim();

            if (interest.isEmpty() || skill.isEmpty() || experience.isEmpty()) {
                Toast.makeText(context, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            // 🟩 Get email from session
            SharedPreferences prefs = context.getSharedPreferences("loginPrefs", Context.MODE_PRIVATE);
            String email = prefs.getString("email", "");

            ApplyJobHelper.applyToJob(context, jobId, email, skill, experience, interest);
            dialog.dismiss();
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView description, skill, experience, duration;
        Button applyBtn;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.jobImage);
            description = itemView.findViewById(R.id.jobDescription);
            skill = itemView.findViewById(R.id.jobSkill);
            experience = itemView.findViewById(R.id.jobExperience);
            duration = itemView.findViewById(R.id.jobDuration);
            applyBtn = itemView.findViewById(R.id.applyBtn);
        }
    }
}
