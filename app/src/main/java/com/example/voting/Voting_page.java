package com.example.voting;

import static java.lang.Integer.parseInt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;

public class Voting_page extends Activity {
    ArrayList<Candidates> candidatesList = new ArrayList<Candidates>();

    CandidateAdapter adapter = null;

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
        adapter = new CandidateAdapter(this, R.layout.candidatedetails, candidatesList);
        gridView.setAdapter(adapter);
        postDataUsingVolley();

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                GlobalVariables.votedList.clear();
                globalClass.setUsername(null);
                globalClass.setUser_id(null);
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
                int id = candidatesList.get(position).getId();
                String name = candidatesList.get(position).getName();
                String section = candidatesList.get(position).getSection();
                String can_position = candidatesList.get(position).getPosition();
                String img = candidatesList.get(position).getImg();
//                highlight();


                for(int i=0; i<GlobalVariables.votedList.size(); i++){
                    if(GlobalVariables.votedList.get(i).getPosition().equals(can_position)){
//                        Toast.makeText(Voting_page.this, "pos exist", Toast.LENGTH_SHORT).show();
                        GlobalVariables.votedList.get(i).setId(id);
                        GlobalVariables.votedList.get(i).setName(name);
                        GlobalVariables.votedList.get(i).setSection(section);
                        GlobalVariables.votedList.get(i).setImg(img);
//                        highlight();
//                        View child = arg0.getChildAt(i);
//                        child.setBackgroundColor(getResources().getColor(R.color.purple_500));
                        return;
                    }
                }
                GlobalVariables.votedList.add(new Candidates(id,name,section,can_position,img));

            }
        });



    }


    private void postDataUsingVolley() {
//        String url = "http://192.168.1.3/php/Web-based-ordering-management-system/mobile/getMenu.php";
//        String url = "http://ucc-csd-bscs.com/WEBOMS/mobile/getMenu.php";
        String url = GlobalVariables.url+"/voting_try.php";

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject respObj = new JSONObject(response);
                    //init
                    String canNameArr = respObj.getString("canNameArr");
                    String courseNameArr = respObj.getString("courseNameArr");
                    String posNameArr = respObj.getString("posNameArr");
                    String picNameArr = respObj.getString("picNameArr");
                    String canIdArr = respObj.getString("canIdArr");
                    //converting to array
                    String[] canNameArr2 = canNameArr.split(",");
                    String[] courseNameArr2 = courseNameArr.split(",");
                    String[] posNameArr2 = posNameArr.split(",");
                    String[] picNameArr2 = picNameArr.split(",");
                    String[] canIdArr2 = canIdArr.split(",");

//                    Toast.makeText(Voting_page.this, canNameArr, Toast.LENGTH_SHORT).show();
                    //proccess
                    for(int i=0; i<canNameArr2.length; i++){
                        String picUrl = GlobalVariables.url+"/webimg/"+picNameArr2[i];
                        candidatesList.add(new Candidates(Integer.parseInt(canIdArr2[i]) , canNameArr2[i], courseNameArr2[i], posNameArr2[i], picUrl));
                    }
//
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

//    private void highlight() {
////        for (int i = 0; i < gridView.getChildCount(); i++) {
////            View child = gridView.getChildAt(i);
////            TextView textView = child.findViewById(R.id.nameText); // Replace with the actual ID of your TextView
////            String gridItemText = textView.getText().toString();
////
////            for (int j = 0; j < GlobalVariables.votedList.size(); j++) {
////                String votedName = GlobalVariables.votedList.get(j).getName();
////
////                if (votedName.equals(gridItemText)) {
////                    child.setBackgroundColor(getResources().getColor(R.color.purple_500));
//////                    Toast.makeText(this, gridItemText, Toast.LENGTH_SHORT).show();
//////                    break; // Break the loop if a match is found
////                } else {
////                    child.setBackgroundColor(getResources().getColor(R.color.white));
////                }
////            }
////        }
//
//
//        ArrayList<String> arrayList = new ArrayList<>();
////        for (int i = 0; i < GlobalVariables.votedList.size(); i++) {
////            arrayList.add(GlobalVariables.votedList.get(i).getName());
////        }
////        Toast.makeText(this, arrayList.toString(), Toast.LENGTH_SHORT).show();
////
//
//        for (int i = 0; i < gridView.getChildCount(); i++) {
//            View child = gridView.getChildAt(i);
//            TextView textView = child.findViewById(R.id.nameText); // Replace with the actual ID of your TextView
//            String gridItemText = textView.getText().toString();
//
//            System.out.println("gridItemText: " + gridItemText);
//
//            if (arrayList.contains(gridItemText)) {
//                child.setBackgroundColor(getResources().getColor(R.color.purple_500));
//                System.out.println("Match found for: " + gridItemText);
//            } else {
//                child.setBackgroundColor(getResources().getColor(R.color.white));
//                System.out.println("No match found for: " + gridItemText);
//            }
//        }
//
//
//    }



//    }

//    private void highlight() {
//        for (int i = 0; i < gridView.getChildCount(); i++) {
//            View child = gridView.getChildAt(i);
//            TextView textView = child.findViewById(R.id.nameText); // Replace with the actual ID of your TextView
//            String gridItemText = textView.getText().toString();
//
//            if (isInVotedList(gridItemText)) {
//                child.setBackgroundColor(getResources().getColor(R.color.purple_500));
//            } else {
//                child.setBackgroundColor(getResources().getColor(android.R.color.transparent));
//            }
//        }
//    }
//
//    private boolean isInVotedList(String itemName) {
//        for (Candidates candidate : GlobalVariables.votedList) {
//            if (candidate.getName().equals(itemName)) {
//                return true;
//            }
//        }
//        return false;
//    }

}