package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class P1Details extends AppCompatActivity {

    Button btn;
    TextView name,mobile,email,links,skills,loc;
    ImageView img;
    String n,m,e,l,s,url,URL;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details);

        name=findViewById(R.id.name1);
        mobile=findViewById(R.id.mobile1);
        email=findViewById(R.id.email1);
        links=findViewById(R.id.links1);
        loc=findViewById(R.id.location1);
        skills=findViewById(R.id.skills1);
        btn=findViewById(R.id.GOto1);
        img=findViewById(R.id.imgpic);

        final  String theId=getIntent().getExtras().getString("test1");
        final String em=getIntent().getExtras().getString("em");
        URL="http://139.59.65.145:9090/user/personaldetail/"+theId;

        final RequestQueue requestQueue= Volley.newRequestQueue(P1Details.this);

        JsonObjectRequest objectRequest=new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(P1Details.this,"Data added !",Toast.LENGTH_LONG).show();
                        try {
                            JSONObject object=response.getJSONObject("data");
                            name.setText(object.getString("name"));
                            mobile.setText(object.getString("mobile_no"));
                            links.setText(object.getString("links"));
                            loc.setText(object.getString("location"));
                            email.setText(em);
                            skills.setText(object.getString("skills"));
                            String uid=object.getString("id");
                        }catch(Exception e){
                            Toast.makeText(P1Details.this,"Really bro ! "+e,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(P1Details.this,"Gaye beta to "+error,Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(objectRequest);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(P1Details.this,Certi.class);
                intent.putExtra("theId",theId);
                intent.putExtra("em",em);
                startActivity(intent);
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(P1Details.this, EduDetails.class);
                intent.putExtra("theId",theId);
                startActivity(intent);
            }
        });
    }
}
