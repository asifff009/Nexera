package com.asif.stepupbd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    EditText emailInput, passwordInput;
    Button loginBtn, signupBtn;
    String URL = "http://192.168.202.232/apps/login.php";  // Update with your local IP

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailInput = findViewById(R.id.emailInput);
        passwordInput = findViewById(R.id.passwordInput);
        loginBtn = findViewById(R.id.loginBtn);
        signupBtn = findViewById(R.id.signupBtn);

        signupBtn.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, SignupActivity.class));
        });

        loginBtn.setOnClickListener(v -> {
            String email = emailInput.getText().toString().trim();
            String passwordRaw = passwordInput.getText().toString().trim();

            if (email.isEmpty() || passwordRaw.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String hashedPassword = sha256(passwordRaw);

            StringRequest request = new StringRequest(Request.Method.POST, URL,
                    response -> {
                        response = response.trim();  // Trim extra whitespace
                        if (response.startsWith("success:")) {
                            String userType = response.split(":")[1];

                            SharedPreferences.Editor editor = getSharedPreferences("myApp", MODE_PRIVATE).edit();
                            editor.putString("email", email);
                            editor.putString("user_type", userType);
                            editor.apply();

                            Intent intent;
                            switch (userType) {
                                case "employee":
                                    intent = new Intent(LoginActivity.this, EmployeePage.class);
                                    break;
                                case "employer":
                                    intent = new Intent(LoginActivity.this, EmployerrPage.class);
                                    break;
                                case "uddokta":
                                    intent = new Intent(LoginActivity.this, UddoktaPage.class);
                                    break;
                                default:
                                    Toast.makeText(LoginActivity.this, "Unknown user type", Toast.LENGTH_SHORT).show();
                                    return;
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(LoginActivity.this, "Login failed: " + response, Toast.LENGTH_SHORT).show();
                        }
                    },
                    error -> Toast.makeText(LoginActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show()
            ) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", hashedPassword);
                    return map;
                }
            };

            Volley.newRequestQueue(LoginActivity.this).add(request);
        });
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
