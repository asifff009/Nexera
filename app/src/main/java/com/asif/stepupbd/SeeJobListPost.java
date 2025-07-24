package com.asif.stepupbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.*;

import java.util.*;

public class SeeJobListPost extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    String baseUrl = "http://192.168.1.102/apps/getData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_job_list_post);

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                baseUrl + "/getData.php", null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject obj = response.getJSONObject(x);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("title", obj.getString("title"));
                            map.put("description", obj.getString("description"));
                            map.put("experience", obj.getString("experience"));
                            map.put("duration", obj.getString("duration"));
                            map.put("location", obj.getString("location"));
                            map.put("contact", obj.getString("contact"));
                            arrayList.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listView.setAdapter(new MyAdapter());
                },
                error -> Log.e("VOLLEY", error.toString()));

        Volley.newRequestQueue(this).add(request);
    }

    class MyAdapter extends BaseAdapter {
        @Override public int getCount() { return arrayList.size(); }
        @Override public Object getItem(int i) { return null; }
        @Override public long getItemId(int i) { return 0; }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.item, null);
            HashMap<String, String> job = arrayList.get(position);

            TextView title = view.findViewById(R.id.textTitle);
            TextView desc = view.findViewById(R.id.textDescription);

            title.setText(job.get("title"));
            desc.setText(job.get("description"));

            view.setOnClickListener(v -> {
                Intent intent = new Intent(SeeJobListPost.this, JobDetailsActivity.class);
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
