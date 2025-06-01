package com.asif.stepupbd;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity {

   // ImageView imageProfile;
    TextView tvChangePhoto;
    TextInputEditText inputEmail, inputPassword, inputName;
    Button buttonSignup, buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

       // imageProfile = findViewById(R.id.imageProfile);
        tvChangePhoto = findViewById(R.id.tvChangePhoto);
        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputName = findViewById(R.id.inputName);
        buttonSignup = findViewById(R.id.buttonSignup);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signup.this, Login.class));
                finish();
            }
        });

        buttonSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String name = inputName.getText().toString();

                // BitmapDrawable bitmapDrawable = (BitmapDrawable) imageProfile.getDrawable();
               // Bitmap bitmap = bitmapDrawable.getBitmap();
               // ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
              //  bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

               // byte[] imageBytes = outputStream.toByteArray();
             //   String image = Base64.encodeToString(imageBytes, Base64.NO_WRAP);  // FIXED

                String url = "http://192.168.120.232/apps/signup.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        new AlertDialog.Builder(Signup.this)
                                .setTitle("Server Response")
                                .setMessage(response)
                                .create().show();

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        new AlertDialog.Builder(Signup.this)
                                .setTitle("Server Response")
                                .setMessage(error.getMessage())
                                .create().show();


                    }
                }){
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map myMap = new HashMap <String,String>();

                        myMap.put("email", email);
                        myMap.put("password", password);
                        myMap.put("name", name);
                       // myMap.put("image", image);

                        return myMap;
                    }
                };


                RequestQueue requestQueue = Volley.newRequestQueue(Signup.this);
                requestQueue.add(stringRequest);


            }
        });


    }
}