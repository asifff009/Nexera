package com.asif.stepupbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.AccessController;

public class MainActivity extends AppCompatActivity {

    TextView map;
    Button buttonMap;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        map = findViewById(R.id.map);
        buttonMap = findViewById(R.id.buttonMap);

        // google map code --> jei button a dorkar oi button er set on click er moddhe diye dibo
        // map er dependency holo : implementation("com.google.maps.android:android-maps-utils:3.10.0")

/*

        Uri gmmIntentUri = Uri.parse("geo:0,0?q="+"Pingna High School ");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        map.getContext().startActivity(mapIntent);

 */

        //=================================================================

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri gmmIntentUri = Uri.parse("geo:0,0?q="+"Pingna High School ");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                buttonMap.getContext().startActivity(mapIntent);

            }
        });


    }
}