package com.asif.stepupbd;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
                showAlert("Encryption Error", e.getMessage());
                return;
            }

            String url = "http://192.168.120.232/apps/employer_signup.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    response -> showAlert("Server Response", response),
                    error -> showAlert("Server Error", error.getMessage())) {

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

    private void showAlert(String title, String message) {
        new AlertDialog.Builder(EmployerSignup.this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", null)
                .create().show();
    }
}