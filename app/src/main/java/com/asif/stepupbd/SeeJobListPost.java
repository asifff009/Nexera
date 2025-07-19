package com.asif.stepupbd;

import android.annotation.SuppressLint;
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
    String baseUrl = "http://192.168.1.101/apps/getData.php";

    @SuppressLint("MissingInflatedId")
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

            ((TextView) view.findViewById(R.id.textTitle)).setText("Title: " + job.get("title"));
            ((TextView) view.findViewById(R.id.textDescription)).setText("Description: " + job.get("description"));
            ((TextView) view.findViewById(R.id.textExperience)).setText("Experience: " + job.get("experience"));
            ((TextView) view.findViewById(R.id.textDuration)).setText("Duration: " + job.get("duration"));
            ((TextView) view.findViewById(R.id.textLocation)).setText("Location: " + job.get("location"));
            ((TextView) view.findViewById(R.id.textContact)).setText("Contact: " + job.get("contact"));

            return view;
        }
    }
}
