package com.asif.stepupbd;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import java.util.List;

public class EmployerJobAdapter extends RecyclerView.Adapter<EmployerJobAdapter.JobViewHolder> {

    Context context;
    List<JobModel> list;
    String employerEmail;

    public EmployerJobAdapter(Context context, List<JobModel> list, String email) {
        this.context = context;
        this.list = list;
        this.employerEmail = email;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new JobViewHolder(LayoutInflater.from(context).inflate(R.layout.item_employer_job, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobModel job = list.get(position);

        Glide.with(context).load(job.getImage()).into(holder.image);
        holder.description.setText("Description: " + job.getDescription());
        holder.skill.setText("Skill: " + job.getSkill());
        holder.experience.setText("Experience: " + job.getExperience());
        holder.duration.setText("Duration: " + job.getDuration());

        holder.deleteBtn.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Confirm Delete")
                    .setMessage("Are you sure you want to delete this job?")
                    .setPositiveButton("Delete", (dialog, which) -> deleteJob(job.getId(), position))
                    .setNegativeButton("Cancel", null)
                    .show();
        });
    }

    private void deleteJob(int jobId, int position) {
        String url = "http://192.168.1.102/apps/delete_job.php?id=" + jobId;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    list.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Job deleted", Toast.LENGTH_SHORT).show();
                },
                error -> Toast.makeText(context, "Failed to delete job", Toast.LENGTH_SHORT).show());

        Volley.newRequestQueue(context).add(request);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class JobViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView description, skill, experience, duration;
        Button deleteBtn;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.jobImage);
            description = itemView.findViewById(R.id.jobDescription);
            skill = itemView.findViewById(R.id.jobSkill);
            experience = itemView.findViewById(R.id.jobExperience);
            duration = itemView.findViewById(R.id.jobDuration);
            deleteBtn = itemView.findViewById(R.id.deleteBtn);
        }
    }
}
