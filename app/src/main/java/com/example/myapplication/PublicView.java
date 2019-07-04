package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.util.HashMap;
import java.util.Map;

public class PublicView extends AppCompatActivity {

    TextView me, col, loca, linking;
    String URL, url;
    Button btn, btn1, btn2, delete;
    private Toolbar toolbar;
    //    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.public_view);

        final String theId = getIntent().getExtras().getString("identity");
        final String em = getIntent().getExtras().getString("em");
        btn = findViewById(R.id.educational);
        btn1 = findViewById(R.id.person);
        btn2 = findViewById(R.id.Prof);

        delete = findViewById(R.id.delete);
        me = findViewById(R.id.me);
        col = findViewById(R.id.col);
        loca = findViewById(R.id.Loca);
        linking = findViewById(R.id.linking);

        URL = "http://139.59.65.145:9090/user/personaldetail/" + theId;

        RequestQueue requestQueue = Volley.newRequestQueue(PublicView.this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
//                            Toast.makeText(PublicView.this, "App started ! " + response.getString("method"), Toast.LENGTH_LONG).show();
                            JSONObject jsonObject = response.getJSONObject("data");
                            me.setText(jsonObject.getString("name"));
                            col.setText(jsonObject.getString("skills"));
                            loca.setText(jsonObject.getString("location"));
                            linking.setText("UID: " + jsonObject.getString("uid"));
                        } catch (Exception e) {
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PublicView.this, "Gaye beta aaj to " + error, Toast.LENGTH_LONG).show();
                    }
                }
        );
        requestQueue.add(objectRequest);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PublicView.this, "Data Deleted", Toast.LENGTH_LONG).show();
                url = "http://139.59.65.145:9090/user/professionaldetail/" + theId;

                Map<String, String> params = new HashMap();
                params.put("uid", theId);
                JSONObject parameters = new JSONObject(params);

                RequestQueue req = Volley.newRequestQueue(PublicView.this);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(PublicView.this,""+theId+em,Toast.LENGTH_LONG).show();
                Intent i = new Intent(PublicView.this, PerDetails.class);
                i.putExtra("test1", theId);
                i.putExtra("em", em);
                startActivity(i);
            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(PublicView.this, EduDetails.class);
                i.putExtra("theId", theId);
                startActivity(i);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PublicView.this, ProfDetails.class);
                intent.putExtra("theId", theId);
                startActivity(intent);

            }
        });
    }
}
