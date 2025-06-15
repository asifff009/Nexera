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

public class ViewApplicationsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    List<ApplicationModel> applicationList;
    ApplicationAdapter adapter;

    int jobId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_applications);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        applicationList = new ArrayList<>();
        adapter = new ApplicationAdapter(this, applicationList);
        recyclerView.setAdapter(adapter);

        jobId = getIntent().getIntExtra("job_id", -1);
        if (jobId != -1) {
            loadApplications(jobId);
        } else {
            Toast.makeText(this, "Invalid Job ID", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadApplications(int jobId) {
        progressBar.setVisibility(View.VISIBLE);

        String url = "http://192.168.1.102/apps/get_applications.php?job_id=" + jobId;

        StringRequest request = new StringRequest(Request.Method.GET, url,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    try {
                        JSONArray array = new JSONArray(response);
                        applicationList.clear();

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject obj = array.getJSONObject(i);
                            applicationList.add(new ApplicationModel(
                                    obj.getString("employee_email"),
                                    obj.getString("skill"),
                                    obj.getString("experience"),
                                    obj.getString("interest")
                            ));
                        }

                        adapter.notifyDataSetChanged();

                    } catch (Exception e) {
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(this, "Failed to load applications", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
