package com.asif.stepupbd;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.*;

import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

import java.util.ArrayList;

public class JobListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    JobAdapter adapter;
    ArrayList<JobModel> jobList;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);

        recyclerView = findViewById(R.id.recyclerViewJobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        adapter = new JobAdapter(this, jobList);
        recyclerView.setAdapter(adapter);

        loadJobs();
    }

    private void loadJobs() {
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading jobs...");
        dialog.show();

        String URL = "http://192.168.1.101/apps/get_jobs.php";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    dialog.dismiss();
                    try {
                        String status = response.getString("status");
                        if (status.equalsIgnoreCase("success")) {
                            jobList.clear();
                            JSONArray jobsArray = response.getJSONArray("jobs");
                            for (int i = 0; i < jobsArray.length(); i++) {
                                JSONObject job = jobsArray.getJSONObject(i);
                                JobModel jm = new JobModel(
                                        job.getInt("id"),
                                        job.getString("description"),
                                        job.getString("skill"),
                                        job.getString("experience"),
                                        job.getString("duration"),
                                        job.getString("posted_by")
                                );
                                jobList.add(jm);
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "Failed to load jobs", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parse error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
