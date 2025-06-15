package com.asif.stepupbd;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class EmployerJobsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<JobModel> jobList;
    EmployerJobAdapter adapter;
    String employerEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_jobs);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        jobList = new ArrayList<>();

        SharedPreferences prefs = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        employerEmail = prefs.getString("email", "");

        adapter = new EmployerJobAdapter(this, jobList, employerEmail);
        recyclerView.setAdapter(adapter);

        loadEmployerJobs();
    }

    private void loadEmployerJobs() {
        progressBar.setVisibility(View.VISIBLE);

        String url = "http://192.168.1.102/apps/get_employer_jobs.php?email=" + employerEmail;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray array = new JSONArray(response);
                        jobList.clear();
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            jobList.add(new JobModel(
                                    obj.getInt("id"),
                                    obj.getString("image"),
                                    obj.getString("description"),
                                    obj.getString("skill"),
                                    obj.getString("experience"),
                                    obj.getString("duration")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(this, "Error parsing jobs", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Error loading jobs", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
