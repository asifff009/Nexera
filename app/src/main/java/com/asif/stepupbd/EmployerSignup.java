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

public class EmployerSignup extends AppCompatActivity {

    TextInputEditText inputName, inputEmail, inputCompanyName, inputBusinessCategory, inputRegiNo, inputCityStreet, inputPassword;
    Button buttonSignup, buttonLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_signup);

        inputName = findViewById(R.id.inputName);
        inputEmail = findViewById(R.id.inputEmail);
        inputCompanyName = findViewById(R.id.inputCompanyName);
        inputBusinessCategory = findViewById(R.id.inputBusinessCategory);
        inputRegiNo = findViewById(R.id.inputRegiNo);
        inputCityStreet = findViewById(R.id.inputCityStreet);
        inputPassword = findViewById(R.id.inputPassword);



        buttonSignup = findViewById(R.id.buttonSignup);
        buttonLogin = findViewById(R.id.buttonLogin);

        buttonLogin.setOnClickListener(v -> {
            startActivity(new Intent(EmployerSignup.this, Login.class));
            finish();
        });

        buttonSignup.setOnClickListener(v -> {

            String name = inputName.getText().toString();
            String email = inputEmail.getText().toString();
            String companyname = inputCompanyName.getText().toString();
            String businesscategory = inputBusinessCategory.getText().toString();
            String regino = inputRegiNo.getText().toString();
            String citystreet = inputCityStreet.getText().toString();
            String password = inputPassword.getText().toString();




            try {
                String encryptedKey = MyMethods.encryptData("aru112211", "aru#123456789123");
                MyMethods.MY_KEY = encryptedKey;
            } catch (Exception e) {
                showAlert("Encryption Error", e.getMessage(), false);
                return;
            }

            String url = "http://192.168.1.102/apps/employer_signup.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> {
                        if (response.contains("Signup Success")) {
                            SharedPreferences sharedPreferences = getSharedPreferences("myApp", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("email", email);
                            editor.apply();

                            // Redirect to EmployeePage
                            Intent intent = new Intent(EmployerSignup.this, EmployerrPage.class);
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
                    myMap.put("companyname", companyname);
                    myMap.put("businesscategory", businesscategory);
                    myMap.put("regino", regino);
                    myMap.put("citystreet", citystreet);
                    myMap.put("password", password);

                    // myMap.put("key", MyMethods.MY_KEY);
                    return myMap;

                }
            };


            RequestQueue requestQueue = Volley.newRequestQueue(EmployerSignup.this);
            requestQueue.add(stringRequest);
        });



    }

    private void showAlert(String title, String message, boolean b) {
        new AlertDialog.Builder(EmployerSignup.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create().show();
    }
}