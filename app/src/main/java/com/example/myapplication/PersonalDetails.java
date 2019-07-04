package com.example.myapplication;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class PersonalDetails extends AppCompatActivity {

    Button btn;
    EditText name, mobile, email, links, skills, location;
    String n, m, e, s, l, lo, URL2;
    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_personal);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        btn = findViewById(R.id.nextper);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        links = findViewById(R.id.links);
        location = findViewById(R.id.location);
        skills = findViewById(R.id.skills);

        final String identity = getIntent().getExtras().getString("identity");

        URL2 = "http://139.59.65.145:9090/user/personaldetail/" + identity;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                n = name.getText().toString();
                m = mobile.getText().toString();
                e = email.getText().toString();
                s = skills.getText().toString();
                lo = location.getText().toString();
                l = links.getText().toString();

                Map<String, String> params = new HashMap();

                params.put("mobile_no", m);
                params.put("name", n);
                params.put("links", l);
                params.put("location", lo);
                params.put("skills", s);
                params.put("uid", identity);

//                Toast.makeText(PersonalDetails.this,""+m+n+e,Toast.LENGTH_LONG).show();

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(PersonalDetails.this);
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
                                    Toast.makeText(PersonalDetails.this, "" + test, Toast.LENGTH_LONG).show();

                                    JSONObject obj = response.getJSONObject("data");
                                    String identity1 = obj.getString("id");

                                    if (test.equals("Invalid")) {
                                        Toast.makeText(PersonalDetails.this, "Please enter correct details", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(PersonalDetails.this, "" + identity + "" + e, Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(PersonalDetails.this, EducationalDetails.class);
                                        intent.putExtra("identity", identity);
                                        intent.putExtra("em", e);
                                        startActivity(intent);
                                    }
                                } catch (Exception ex) {
                                    Toast.makeText(PersonalDetails.this, "" + ex, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PersonalDetails.this, "Failure", Toast.LENGTH_LONG).show();

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
