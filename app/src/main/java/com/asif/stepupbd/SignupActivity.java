package com.asif.stepupbd;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    EditText signupName, signupEmail, signupContact, signupAddress, signupPassword;
    Spinner userTypeSpinner;
    Button signupBtn;
    String URL = "http://192.168.1.102/apps/signup.php"; // Replace with your server IP

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupName = findViewById(R.id.signupName);
        signupEmail = findViewById(R.id.signupEmail);
        signupContact = findViewById(R.id.signupContact);
        signupAddress = findViewById(R.id.signupAddress);
        signupPassword = findViewById(R.id.signupPassword);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        signupBtn = findViewById(R.id.signupBtn);

        // Setup Spinner
        String[] userTypes = {"employee", "employer", "uddokta"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userTypeSpinner.setAdapter(adapter);

        signupBtn.setOnClickListener(v -> {
            String name = signupName.getText().toString().trim();
            String email = signupEmail.getText().toString().trim();
            String contact = signupContact.getText().toString().trim();
            String address = signupAddress.getText().toString().trim();
            String password = signupPassword.getText().toString().trim();
            String userType = userTypeSpinner.getSelectedItem().toString().trim();

            if (name.isEmpty() || email.isEmpty() || contact.isEmpty() || address.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            String hashedPassword = sha256(password);
            signupUser(name, email, contact, address, hashedPassword, userType);
        });
    }

    private void signupUser(String name, String email, String contact, String address, String hashedPassword, String userType) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Signing up...");
        dialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, URL,
                response -> {
                    dialog.dismiss();
                    try {
                        JSONObject json = new JSONObject(response);
                        String status = json.getString("status");
                        String message = json.getString("message");

                        if (status.equalsIgnoreCase("success")) {
                            Toast.makeText(this, "Signup successful!", Toast.LENGTH_SHORT).show();

                            // Save session data
                            getSharedPreferences("myApp", MODE_PRIVATE).edit()
                                    .putString("email", email)
                                    .putString("user_type", userType)
                                    .apply();

                            Intent intent;
                            switch (userType) {
                                case "employee":
                                    intent = new Intent(SignupActivity.this, EmployeePage.class);
                                    break;
                                case "employer":
                                    intent = new Intent(SignupActivity.this, EmployerrPage.class);
                                    break;
                                case "uddokta":
                                    intent = new Intent(SignupActivity.this, UddoktaPage.class);
                                    break;
                                default:
                                    Toast.makeText(this, "Unknown user type", Toast.LENGTH_SHORT).show();
                                    return;
                            }

                            startActivity(intent);
                            finish();

                        } else if (status.equalsIgnoreCase("exist")) {
                            Toast.makeText(this, "User already exists", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "Signup failed: " + message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Response parse error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    dialog.dismiss();
                    Toast.makeText(this, "Network error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("contact", contact);
                map.put("address", address);
                map.put("password", hashedPassword);
                map.put("user_type", userType);
                return map;
            }
        };

        Volley.newRequestQueue(this).add(request);
    }

    public static String sha256(String base) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
