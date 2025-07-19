package com.asif.stepupbd;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewJobs extends AppCompatActivity {

    ListView listView;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>(); // 🔁 moved to class-level

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_jobs);

        listView = findViewById(R.id.listView);
        String baseUrl = "http://192.168.1.101/apps";

        // ✅ Volley request to fetch data
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                baseUrl + "/getData.php", null,
                response -> {
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

                    // ✅ Use correct adapter instantiation
                    listView.setAdapter(new MyAdapter());
                },
                error -> Log.e("VOLLEY", error.toString()));

        Volley.newRequestQueue(this).add(request);
    }

    // ✅ Custom Adapter Class
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return arrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

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
