package com.example.voting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class CandidatesList extends AppCompatActivity {

    ArrayList<Candidates> candidatesList = new ArrayList<Candidates>();

    CandidateAdapter adapter = null;


    Button btnBack;
    Intent Callthis;

    GridView gridView;
    String globalPos = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.candidates_list);

        Intent receivedIntent = getIntent();
        String receivedValue = receivedIntent.getStringExtra("position");
        globalPos  = receivedValue;

        gridView = (GridView) findViewById(R.id.gridView1);
        adapter = new CandidateAdapter(this, R.layout.candidatedetails, candidatesList);
        gridView.setAdapter(adapter);
        postDataUsingVolley();

        btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Callthis = new Intent(".Voting_page");
                startActivity(Callthis);
            }
        });
    }

    private void postDataUsingVolley() {
        String url = GlobalVariables.url+"/voting_try.php";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    //init
                    String canNameArr = respObj.getString("canNameArr");
                    String posNameArr = respObj.getString("posNameArr");
                    String picNameArr = respObj.getString("picNameArr");
                    String canIdArr = respObj.getString("canIdArr");
                    //converting to array
                    String[] canNameArr2 = canNameArr.split(",");
                    String[] posNameArr2 = posNameArr.split(",");
                    String[] picNameArr2 = picNameArr.split(",");
                    String[] canIdArr2 = canIdArr.split(",");

                    for(int i=0; i<canIdArr2.length; i++){
                        if(posNameArr2[i].equals(globalPos)){
                            candidatesList.add(new Candidates(Integer.parseInt(canIdArr2[i]) , canNameArr2[i],  posNameArr2[i], null));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("post", "votMobile");
                return params;
            }
        };
        queue.add(request);
    }


}
