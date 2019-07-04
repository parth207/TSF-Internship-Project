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

public class ProfDetails extends AppCompatActivity {

    TextView org,designation,start,end;
    String URL;
    Button btn,btn1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professional_details);

        org=findViewById(R.id.org1);
        designation=findViewById(R.id.Designation1);
        start=findViewById(R.id.since1);
        end=findViewById(R.id.to1);
        btn1=findViewById(R.id.update);
        btn=findViewById(R.id.goback);
        final String theId=getIntent().getExtras().getString("theId");

        final String em=getIntent().getExtras().getString("em");

        URL="http://139.59.65.145:9090/user/professionaldetail/"+theId;

        RequestQueue requestQueue= Volley.newRequestQueue(ProfDetails.this);

        JsonObjectRequest objectRequest=new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object1=response.getJSONObject("data");
                            String chec=object1.getString("id");
                            Toast.makeText(ProfDetails.this, "Id= "+chec , Toast.LENGTH_LONG).show();
                            org.setText(object1.getString("organisation"));
                            designation.setText(object1.getString("designation"));
                            start.setText(object1.getString("start_date"));
                            end.setText(object1.getString("end_date"));

                        }catch (Exception e){

                            Toast.makeText(ProfDetails.this,""+e,Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ProfDetails.this,"Gaye The "+error,Toast.LENGTH_LONG).show();
                    }
                }
        );

        requestQueue.add(objectRequest);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfDetails.this, PublicView.class);
                intent.putExtra("identity",theId);
                startActivity(intent);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ProfDetails.this,ProfessionalDetails.class);
                intent.putExtra("identity",theId);
                intent.putExtra("em",em);
                startActivity(intent);
            }
        });
    }
}
