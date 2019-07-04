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

public class ProfessionalDetails extends AppCompatActivity {
    Button btn;
    EditText org,Designation,start,end;
    String co,su,lo,si,ti,URL2;
    AnimationDrawable animationDrawable;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_professional);

        linearLayout = (LinearLayout)findViewById(R.id.linearlayout);
        animationDrawable = (AnimationDrawable) linearLayout.getBackground();
        animationDrawable.setEnterFadeDuration(5000);
        animationDrawable.setExitFadeDuration(2000);

        btn = findViewById(R.id.subprof);
        org = findViewById(R.id.org);
        Designation = findViewById(R.id.Designation);
        start = findViewById(R.id.start_Date);
        end = findViewById(R.id.end_Date);

        final String theID=getIntent().getExtras().getString("identity");
        URL2 = "http://139.59.65.145:9090/user/professionaldetail/" + theID;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                co = org.getText().toString();
                su = Designation.getText().toString();
                ti = end.getText().toString();
                si = start.getText().toString();

                Map<String, String> params = new HashMap();

                params.put("organisation", co);
                params.put("designation", su);
                params.put("end_date", ti);
                params.put("start_date", si);
                params.put("uid", theID);

//                Toast.makeText(PersonalDetails.this,""+m+n+e,Toast.LENGTH_LONG).show();

                JSONObject parameters = new JSONObject(params);

                RequestQueue requestQueue = Volley.newRequestQueue(ProfessionalDetails.this);
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
                                    Toast.makeText(ProfessionalDetails.this, "" + test, Toast.LENGTH_LONG).show();

                                    JSONObject obj = response.getJSONObject("data");
                                    String identity1 = obj.getString("id");

                                    if (test.equals("Invalid")) {
                                        Toast.makeText(ProfessionalDetails.this, "Please enter correct details", Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(ProfessionalDetails.this, "" + theID + "", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(ProfessionalDetails.this, PublicView.class);
                                        intent.putExtra("identity", theID);
                                        startActivity(intent);
                                    }
                                } catch (Exception ex) {
                                    Toast.makeText(ProfessionalDetails.this, "" + ex, Toast.LENGTH_LONG).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfessionalDetails.this, "Failure", Toast.LENGTH_LONG).show();
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
