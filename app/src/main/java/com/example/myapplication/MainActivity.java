package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String URL1, URL2;
    EditText text, pass;
    String mail;
    Button sign, login;
    String URL, email, password;
    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        text = findViewById(R.id.emailfirst);
        sign = findViewById(R.id.SignUpFirst);
        login = findViewById(R.id.LoginFirst);
        pass = findViewById(R.id.passfirst);

        URL = "http://139.59.65.145:9090/test/";
        URL1 = "http://139.59.65.145:9090/user/login/";
        URL2 = "http://139.59.65.145:9090/user/signup/";

        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Toast.makeText(MainActivity.this, "App started ! " + response.getString("method"), Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Gaye beta aaj to " + error, Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(objectRequest);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = text.getText().toString();
                password = pass.getText().toString();

                Map<String, String> params = new HashMap();
                params.put("email", email);
                params.put("password", password);

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest postRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL1,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
//                                TODO: handle success
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                try {
                                    JSONObject obj1 = response.getJSONObject("data");
                                    String test1 = obj1.getString("id");
                                    String test = response.toString().substring(19, 26);
                                    Toast.makeText(MainActivity.this, "" + test1, Toast.LENGTH_LONG).show();

                                    if (test.equals("Invalid")) {
                                        Toast.makeText(MainActivity.this, "Please enter correct Id or Password", Toast.LENGTH_LONG).show();
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, PublicView.class);
                                        intent.putExtra("identity", test1);
                                        intent.putExtra("em", email);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_LONG).show();

                        error.printStackTrace();
                        //TODO: handle failure
                    }
                });
                requestQueue.add(postRequest);
            }
        });

        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = text.getText().toString();
                password = pass.getText().toString();

                Map<String, String> params = new HashMap();
                params.put("email", email);
                params.put("password", password);

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
                JsonObjectRequest postRequest = new JsonObjectRequest(
                        Request.Method.POST,
                        URL2,
                        parameters,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
//                                TODO: handle success
//                                Toast.makeText(MainActivity.this, "Success"+response.getString("method"), Toast.LENGTH_SHORT).show();
                                try {
                                    String test = response.toString();
                                    Toast.makeText(MainActivity.this, "" + test, Toast.LENGTH_LONG).show();

                                    JSONObject obj = response.getJSONObject("data");
                                    String identity = obj.getString("id");

                                    if (test.equals("Invalid")) {
                                        Toast.makeText(MainActivity.this, "Please enter correct Id or Password", Toast.LENGTH_LONG).show();
                                    } else {
                                        Intent intent = new Intent(MainActivity.this, PersonalDetails.class);
                                        intent.putExtra("identity", identity);
                                        startActivity(intent);
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(MainActivity.this, "" + e, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Failure", Toast.LENGTH_LONG).show();

                        error.printStackTrace();
                        //TODO: handle failure
                    }
                });
                requestQueue.add(postRequest);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (animationDrawable != null && animationDrawable.isRunning())
            animationDrawable.stop();
    }
}
