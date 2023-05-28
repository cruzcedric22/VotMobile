package com.example.voting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Qr_scanner extends AppCompatActivity {

    Button scan_btn;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_scanner);

        scan_btn = findViewById(R.id.scanner);
        textView = findViewById(R.id.text);

        scan_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Qr_scanner.this);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setPrompt("Scan a QR code");
                intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(intentResult != null){
            String contents = intentResult.getContents();
            if (contents != null){
                textView.setText(contents);
                postDataUsingVolley(contents);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void postDataUsingVolley(final String qrVal) {
        String url = GlobalVariables.url+"/qr_mob_api.php";
//        String url = "http://192.168.1.12/VOT/qr_mob_api.php";
//        loadingPB.setVisibility(View.VISIBLE);
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
//                loadingPB.setVisibility(View.GONE);
                try {
                    JSONObject respObj = new JSONObject(response);
                    String result = respObj.getString("result");

                    Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
//                    if (result.equals("valid")){
//                        finish();
//                        GlobalClass globalClass = (GlobalClass) getApplicationContext();
//                        globalClass.setUser_id(respObj.getString("id"));
//                    }
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
                GlobalClass globalClass = (GlobalClass) getApplicationContext();
                Map<String, String> params = new HashMap<String, String>();
                params.put("qrScanMob", qrVal);
//                params.put("pass",);
//                params.put("post", "VOT");
                return params;
            }
        };
        queue.add(request);
    }

}
