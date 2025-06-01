package com.asif.stepupbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

   SharedPreferences sharedPreferences;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("myApp",MODE_PRIVATE);

        try {
            MyMethods.MY_KEY = MyMethods.encryptData("aru112211","aru#123456789123");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        String email = sharedPreferences.getString("email", "");

        if(email.length()<=0){
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();

        }



    }
}