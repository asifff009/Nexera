package com.asif.stepupbd;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SeeApplyList extends AppCompatActivity {

    ListView listView;
    ProgressBar progressBar;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<>();
    String baseUrl = "http://192.168.1.102/apps/getApplyData.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_apply_list);

        listView = findViewById(R.id.listView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                baseUrl + "/getApplyData.php", null,
                response -> {
                    progressBar.setVisibility(View.GONE);
                    for (int x = 0; x < response.length(); x++) {
                        try {
                            JSONObject obj = response.getJSONObject(x);
                            HashMap<String, String> map = new HashMap<>();
                            map.put("name", obj.getString("name"));
                            map.put("interest", obj.getString("interest"));
                            map.put("experience", obj.getString("experience"));
                            map.put("skill", obj.getString("skill"));
                            map.put("location", obj.getString("location"));
                            map.put("contact", obj.getString("contact"));
                            arrayList.add(map);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listView.setAdapter(new SeeApplyList.MyAdapter());
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

            title.setText(job.get("name"));
            desc.setText(job.get("interest"));

            view.setOnClickListener(v -> {
                Intent intent = new Intent(SeeApplyList.this, ApplyDetails.class);
                intent.putExtra("name", job.get("name"));
                intent.putExtra("interest", job.get("interest"));
                intent.putExtra("experience", job.get("experience"));
                intent.putExtra("skill", job.get("skill"));
                intent.putExtra("location", job.get("location"));
                intent.putExtra("contact", job.get("contact"));
                startActivity(intent);
            });

            return view;
        }
    }
}
