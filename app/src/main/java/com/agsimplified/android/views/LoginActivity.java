package com.agsimplified.android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.agsimplified.android.AgSimplified;
import com.agsimplified.android.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

// http://lucatironi.net/tutorial/2012/10/16/ruby_rails_android_app_authentication_devise_tutorial_part_two/
public class LoginActivity extends AgSimplifiedActivity {
    private SharedPreferences mPrefs;
    private String mEmail;
    private String mPassword;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        
        mPrefs = getSharedPreferences("CurrentUser", MODE_PRIVATE);
    }

    public void login(View v) {
        Log.d("nfs", "LOGIN");
        EditText emailField = findViewById(R.id.emailField);
        mEmail = emailField.getText().toString();
        EditText passwordField = findViewById(R.id.passwordField);
        mPassword = passwordField.getText().toString();

        if (TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPassword)) {
            Toast.makeText(this, "Email and Password are required.", Toast.LENGTH_LONG).show();
            return;
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = AgSimplified.getApiUrl() + "/sessions";
        try {
            JSONObject params = new JSONObject();
            JSONObject userParams = new JSONObject();
            userParams.put("email", mEmail);
            userParams.put("password", mPassword);
            params.put("user", userParams);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, params,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.i("nfs", "RESPONSE");
                            Log.i("nfs", response.toString());
                            JSONObject data = null;
                            try {
                                data = response.getJSONObject("data");
                                String authToken = data.getString("auth_token");
                                SharedPreferences.Editor editor = mPrefs.edit();
                                editor.putString("auth_token", authToken);
                                editor.apply();
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
                public Map<String, String> getHeaders() throws AuthFailureError {
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