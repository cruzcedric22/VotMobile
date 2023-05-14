package com.example.voting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class votedList extends Activity {
    Button btnBack,btnSubmit;

    CandidateAdapter adapter = null;
    GridView gridView;
    Intent Callthis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voted_list);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new CandidateAdapter(this, R.layout.candidatedetails, GlobalVariables.votedList);
        gridView.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Callthis = new Intent(".Voting_page");
                startActivity(Callthis);
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataUsingVolley();
            }
        });


    }


    private void postDataUsingVolley() {
        String url = GlobalVariables.url+"/process_voting_mob.php";
//        String url = "https://ucc-csd-bscs.com/WEBOMS/mobile/login.php";
//        loadingPB.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(votedList.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                loadingPB.setVisibility(View.GONE);
                try {
                    JSONObject respObj = new JSONObject(response);
                    String result = respObj.getString("result");
                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    if (result.equals("success")){
                        Toast.makeText(votedList.this, "Voted Successfully", Toast.LENGTH_SHORT).show();
                        GlobalClass globalClass = (GlobalClass) getApplicationContext();
                        GlobalVariables.votedList.clear();
                        globalClass.setUsername(null);
                        globalClass.setUser_id(null);
                        Callthis = new Intent(".Login");
                        startActivity(Callthis);
                        finish();

                    }
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
                String can_id = "";
                for(int i=0; i<GlobalVariables.votedList.size(); i++){
                    if (i == GlobalVariables.votedList.size()-1){
                        can_id += GlobalVariables.votedList.get(i).getId();
                    }
                    else{
                        can_id += GlobalVariables.votedList.get(i).getId() + ",";
                    }
                }
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                Map<String, String> params = new HashMap<String, String>();
                params.put("post", "votMobile");
                params.put("can_id", can_id);
                params.put("voters_id", globalClass.getUser_id());
                params.put("voters_username", globalClass.getUsername());
                return params;
            }
        };
        queue.add(request);
    }

}
