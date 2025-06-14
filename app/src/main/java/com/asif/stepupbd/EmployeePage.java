package com.asif.stepupbd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class EmployeePage extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    Button logoutBtn;
    JobAdapter adapter;
    List<JobModel> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_page);

        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar);
        logoutBtn = findViewById(R.id.logoutBtn);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        jobList = new ArrayList<>();
        adapter = new JobAdapter(this, jobList);
        recyclerView.setAdapter(adapter);

        loadJobs();

        logoutBtn.setOnClickListener(v -> {
            // Clear shared preferences (login session)
            SharedPreferences preferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.apply();

            // Redirect to LoginActivity and clear activity stack
            Intent intent = new Intent(EmployeePage.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void loadJobs() {
        progressBar.setVisibility(View.VISIBLE);
        String url = "http://192.168.1.102/apps/get_jobs.php";

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
                        Toast.makeText(EmployeePage.this, "Failed to parse jobs", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(EmployeePage.this, "Failed to load jobs", Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }
}
