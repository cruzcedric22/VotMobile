package com.example.voting;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edtEmail;
//    ProgressBar loadingPB;
    Intent Callthis;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtEmail=findViewById(R.id.edtEmail);

        btnSubmit=findViewById(R.id.btnLogin);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                postDataUsingVolley(edtEmail.getText().toString());

            }
        });


    }

    private void postDataUsingVolley(final String email) {
        String url = GlobalVariables.url+"/process_log_mob.php";
//        String url = "http://192.168.1.9/AlumniVot/process_log_mob.php";
//        loadingPB.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(Login.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                loadingPB.setVisibility(View.GONE);
                try {
                    JSONObject respObj = new JSONObject(response);
                    String result = respObj.getString("result");

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                    if (result.equals("success")){
                        finish();
                        Callthis = new Intent(".Voting_page");
                        GlobalClass globalClass = (GlobalClass) getApplicationContext();
                        globalClass.setUser_id(respObj.getString("id"));
//                        Toast.makeText(globalClass, globalClass.getUser_id(), Toast.LENGTH_SHORT).show();
                        globalClass.setUsername(edtEmail.getText().toString());
//                        Toast.makeText(Login.this, globalClass.getUser_id(), Toast.LENGTH_SHORT).show();
                        startActivity(Callthis);
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
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
//                params.put("post", "VOT");
                return params;
            }
        };
        queue.add(request);
    }
}