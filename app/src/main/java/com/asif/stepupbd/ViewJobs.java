package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.*;
import java.util.*;

public class ViewJobs extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<HashMap<String, String>> jobList = new ArrayList<>();
    String baseUrl = "http://192.168.1.102/apps/getData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, baseUrl, null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("title", obj.getString("title"));
                            map.put("description", obj.getString("description"));
                            map.put("experience", obj.getString("experience"));
                            map.put("duration", obj.getString("duration"));
                            map.put("location", obj.getString("location"));
                            map.put("contact", obj.getString("contact"));
                            jobList.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listView.setAdapter(new JobAdapter());
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ViewJobs.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        Volley.newRequestQueue(this).add(request);
    }

    class JobAdapter extends BaseAdapter {
        @Override public int getCount() { return jobList.size(); }
        @Override public Object getItem(int i) { return jobList.get(i); }
        @Override public long getItemId(int i) { return i; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item, parent, false);
            HashMap<String, String> job = jobList.get(position);

            TextView textTitle = view.findViewById(R.id.textTitle);
            TextView textDescription = view.findViewById(R.id.textDescription);

            textTitle.setText("Title: " + job.get("title"));
            String desc = job.get("description");
            if (desc.length() > 40) desc = desc.substring(0, 40) + "...";
            textDescription.setText("Description: " + desc);

            view.setOnClickListener(v -> {
                Intent intent = new Intent(ViewJobs.this, JobDetailsActivity2.class);
                intent.putExtra("title", job.get("title"));
                intent.putExtra("description", job.get("description"));
                intent.putExtra("experience", job.get("experience"));
                intent.putExtra("duration", job.get("duration"));
                intent.putExtra("location", job.get("location"));
                intent.putExtra("contact", job.get("contact"));
                startActivity(intent);
            });

            return view;
        }
    }
}
