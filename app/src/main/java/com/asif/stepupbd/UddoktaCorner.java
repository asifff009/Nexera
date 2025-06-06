package com.asif.stepupbd;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class UddoktaCorner extends AppCompatActivity {

    TextInputEditText inputName, inputEmail, inputPhoneNumber, inputPassword, inputHobby;
    Button buttonSignup, buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uddokta_corner);


        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputPhoneNumber = findViewById(R.id.inputPhoneNumber);
        inputPassword = findViewById(R.id.inputPassword);
        inputHobby = findViewById(R.id.inputHobby);

        buttonSignup = findViewById(R.id.buttonSignup);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> {
            startActivity(new Intent(UddoktaCorner.this, Login.class));
            finish();
        });

        buttonSignup.setOnClickListener(v -> {

            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String phonenumber = inputPhoneNumber.getText().toString();
            String password = inputPassword.getText().toString();
            String hobby = inputHobby.getText().toString();



            try {
                String encryptedKey = MyMethods.encryptData("aru112211", "aru#123456789123");
                MyMethods.MY_KEY = encryptedKey;
            } catch (Exception e) {
                showAlert("Encryption Error", e.getMessage(), false);
                return;
            }

            String url = "http://192.168.17.232/apps/uddokta_corner.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        if (response.contains("Signup Success")) {
                            SharedPreferences sharedPreferences = getSharedPreferences("myApp", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.apply();

                            // Redirect to EmployeePage
                            Intent intent = new Intent(UddoktaCorner.this, UddoktaPage.class);
                            startActivity(intent);
                            finish();
                        } else {
                            showAlert("Signup Failed", response, false);
                        }
                    },
                    error -> showAlert("Server Error", error.getMessage(), false)) {

                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> myMap = new HashMap<>();
                    myMap.put("name", name);
                    myMap.put("email", email);
                    myMap.put("phonenumber", phonenumber);
                    myMap.put("password", password);
                    myMap.put("hobby", hobby);
                    // myMap.put("key", MyMethods.MY_KEY);
                    return myMap;

                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(UddoktaCorner.this);
            requestQueue.add(stringRequest);
        });

    }

    private void showAlert(String title, String message, boolean b) {
        new AlertDialog.Builder(UddoktaCorner.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create().show();
    }
}