package com.example.voting;

import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.voting.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

public class Voting_page extends Activity {
    ArrayList<Candidates> candidatesList = new ArrayList<Candidates>();

    String[] canNameArr2 = null;
    String[] posNameArr2 = null;
    String[] picNameArr2 = null;
    String[] canIdArr2 = null;

    PositionAdapter adapter = null;

    Intent Callthis;

    GridView gridView;
    Button btnLogout,btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voting_page);

        btnLogout = (Button) findViewById(R.id.btnLogout);
        btnNext = (Button) findViewById(R.id.btnNext);



        gridView = (GridView) findViewById(R.id.gridView);
        adapter = new PositionAdapter(this, R.layout.positiondetails, candidatesList);
        gridView.setAdapter(adapter);
        postDataUsingVolley();





        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                GlobalVariables.votedList.clear();
                globalClass.setUsername(null);
                Callthis = new Intent(".Login");
                startActivity(Callthis);
                finish();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Callthis = new Intent(".votedList");
                startActivity(Callthis);
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
                String can_position = candidatesList.get(position).getPosition();
                Callthis = new Intent(".CandidatesList");
                Callthis.putExtra("position", can_position);
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
                    canNameArr2 = canNameArr.split(",");
                    posNameArr2 = posNameArr.split(",");
                    picNameArr2 = picNameArr.split(",");
                    canIdArr2 = canIdArr.split(",");

                    LinkedHashSet<String> uniqueValuesSet = new LinkedHashSet<>(Arrays.asList(posNameArr2));
                    String[] uniqueValuesArray = uniqueValuesSet.toArray(new String[0]);
                    for(int i=0; i<uniqueValuesArray.length; i++){
                        candidatesList.add(new Candidates(0 , null, uniqueValuesArray[i], null));
                        GlobalVariables.votedList.add(new Candidates(0,"",uniqueValuesArray[i],"null"));
                    }
                    Toast.makeText(getApplicationContext(), String.valueOf(GlobalVariables.votedList.get(0).getId()), Toast.LENGTH_SHORT).show();

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