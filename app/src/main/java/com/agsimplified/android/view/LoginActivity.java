package com.agsimplified.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.agsimplified.android.AgSimplified;
import com.agsimplified.android.R;
import com.agsimplified.android.util.NetworkRequestQueue;
import com.agsimplified.android.util.SharedPref;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

// http://lucatironi.net/tutorial/2012/10/16/ruby_rails_android_app_authentication_devise_tutorial_part_two/
public class LoginActivity extends AgSimplifiedActivity {
    private EditText emailField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        emailField = findViewById(R.id.emailField);
        emailField.setText(SharedPref.read(SharedPref.Pref.USER_ID, ""));
    }

    public void login(View v) {
        Log.d("nfs", "LoginActivity.login");
        String email = emailField.getText().toString();
        EditText passwordField = findViewById(R.id.passwordField);
        String password = passwordField.getText().toString();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email and Password are required.", Toast.LENGTH_LONG).show();
            return;
        }

        SharedPref.write(SharedPref.Pref.USER_ID, email);

        RequestQueue queue = NetworkRequestQueue.getRequestQueue();
        final String url = AgSimplified.getApiUrl() + "/sessions";
        try {
            JSONObject params = new JSONObject();
            JSONObject userParams = new JSONObject();
            userParams.put("email", email);
            userParams.put("password", password);
            params.put("user", userParams);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("nfs", "LoginActivity.request.onResponse(" + url +")");
                            Log.i("nfs", response.toString());
                            JSONObject data;
                            try {
                                data = response.getJSONObject("data");
                                String authToken = data.getString("auth_token");
                                SharedPref.write(SharedPref.Pref.AUTH_TOKEN, authToken);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(LoginActivity.this, "There was a problem saving your credentials.", Toast.LENGTH_LONG).show();
                            }
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("nfs", "ERROR");
                            Log.e("nfs", error.toString());
                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                        }
                    }) {
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            queue.add(request);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(LoginActivity.this, "There was a problem logging you in.", Toast.LENGTH_LONG).show();
        }
    }
}