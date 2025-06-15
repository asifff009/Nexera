package com.asif.stepupbd;

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

public class ViewJobsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    JobAdapter adapter;
    List<JobModel> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        adapter = new JobAdapter(this, jobList);
        recyclerView.setAdapter(adapter);

        loadJobs();
    }

    private void loadJobs() {
        progressBar.setVisibility(View.VISIBLE);

        String url = "http://192.168.1.102/apps/get_jobs.php";  // Make sure this matches your backend URL

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray array = new JSONArray(response);
                        jobList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);

                            // Defensive check for 'image' field in JSON
                            String imageUrl = "";
                            if (obj.has("image") && !obj.isNull("image")) {
                                imageUrl = obj.getString("image");
                            }

                            jobList.add(new JobModel(
                                    obj.getInt("id"),
                                    imageUrl,
                                    obj.getString("description"),
                                    obj.getString("skill"),
                                    obj.getString("experience"),
                                    obj.getString("duration")
                            ));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Toast.makeText(ViewJobsActivity.this, "Failed to parse jobs", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ViewJobsActivity.this, "Failed to load jobs", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
