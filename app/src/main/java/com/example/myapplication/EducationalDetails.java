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

public class EducationalDetails extends AppCompatActivity {

    Button btn;
    EditText college,sub,location,since,till;
    String co,su,lo,si,ti,URL2;
    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_educational);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        btn = findViewById(R.id.edunext);
        college = findViewById(R.id.college);
        sub = findViewById(R.id.Subject);
        location = findViewById(R.id.checkBox);
        since = findViewById(R.id.sinceDate);
        till = findViewById(R.id.fromDate);

        final String identity = getIntent().getExtras().getString("identity");
        final String em=getIntent().getExtras().getString("em");

        URL2 = "http://139.59.65.145:9090/user/educationdetail/" + identity;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                co = college.getText().toString();
                su = sub.getText().toString();
                ti = till.getText().toString();
                si = since.getText().toString();
                lo = location.getText().toString();

                Map<String, String> params = new HashMap();

                params.put("organisation", co);
                params.put("degree", su);
                params.put("end_year", ti);
                params.put("start_year", si);
                params.put("location", lo);
                params.put("uid", identity);

//                Toast.makeText(PersonalDetails.this,""+m+n+e,Toast.LENGTH_LONG).show();

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(EducationalDetails.this);
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
                                    Toast.makeText(EducationalDetails.this, "" + test, Toast.LENGTH_LONG).show();

                                    JSONObject obj = response.getJSONObject("data");

                                    if (test.equals("Invalid")) {
                                        Toast.makeText(EducationalDetails.this, "Please enter correct details", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(EducationalDetails.this, "" + identity + "", Toast.LENGTH_LONG).show();

                                        Intent intent = new Intent(EducationalDetails.this, ProfessionalDetails.class);
                                        intent.putExtra("identity", identity);
                                        intent.putExtra("em",em);
                                        startActivity(intent);
                                    }
                                } catch (Exception ex) {
                                    Toast.makeText(EducationalDetails.this, "" + ex, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EducationalDetails.this, "Failure", Toast.LENGTH_LONG).show();

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
