package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class EduDetails extends AppCompatActivity {

    TextView org,degree,loc,since,till,certi;
    String URL;
    Button btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.educational_details);

        final String theId=getIntent().getExtras().getString("theId");
        final String em=getIntent().getExtras().getString("em");

        URL="http://139.59.65.145:9090/user/educationdetail/"+theId;

        org=findViewById(R.id.college1);
        degree=findViewById(R.id.Subject1);
        loc=findViewById(R.id.loc1);
        since=findViewById(R.id.sinceDate1);
        till=findViewById(R.id.tillDate1);
        btn=findViewById(R.id.edunext1);
        certi=findViewById(R.id.certificates1);

        RequestQueue requestQueue= Volley.newRequestQueue(EduDetails.this);

        JsonObjectRequest objectRequest=new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object=response.getJSONObject("data");
                            String chec=object.getString("id");
                            Toast.makeText(EduDetails.this, "Id= "+chec , Toast.LENGTH_LONG).show();
                            org.setText(object.getString("organisation"));
                            degree.setText(object.getString("degree"));
                            since.setText(object.getString("start_year"));
                            loc.setText(object.getString("location"));
                            till.setText(object.getString("end_year"));
                        }catch (Exception e){
                            Toast.makeText(EduDetails.this,""+e,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EduDetails.this,"Please Enter Data First"+error,Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(objectRequest);

        //for the image
        certi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EduDetails.this, Certificate.class);
                intent.putExtra("theId",theId);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(EduDetails.this, ProfDetails.class);
                intent.putExtra("theId",theId);
                intent.putExtra("em",em);
                startActivity(intent);
            }
        });
    }
}
