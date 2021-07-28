package com.example.productget;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    TextView text;
    private RequestQueue vQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextInputLayout inputField = findViewById(R.id.textInputLayout);
        Button inputButton = findViewById(R.id.button);
        text = findViewById(R.id.textView);
        vQueue = Volley.newRequestQueue(this);
        Button addButton = findViewById(R.id.add_button);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String url = "http://10.0.2.2:5000/api/products/";
//                JSONObject data = null;
//                try {
//                    String dataset = "{'productId':1,'name':'Z Book 15','price': 455.2,\"categoryId\": 1,\n" + "\"supplierId\": 1}";
//                    data = new JSONObject(dataset);
//                }catch (JSONException e){
//                    e.printStackTrace();
//                }
//
//                try {
//                    postData(url,data);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
                addProductDailog box = new addProductDailog();
                box.show(getSupportFragmentManager(),null);
            }
        });

        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSearch = inputField.getEditText().getText().toString();
                String url = "http://10.0.2.2:5000/api/products/" + toSearch;

                getData(url);
                Log.d(TAG, "onClick: Search was of " + toSearch);
            }
        });
    }

    public void getData(String input) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, input, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: Response listener Triggered.");
                        TextView text = findViewById(R.id.textView);

                        if (text == null) {
                            Log.d(TAG, "onClick: NULL");
                        } else {
                            try {

                                String productName = response.getString("name");
                                String productPrice = response.getString("price");
                                text.setText(String.format("Response:\n\nProduct name: %s\nProduct price: %s", productName, productPrice));
                            } catch (JSONException e) {
                                Log.d(TAG, "onJSON Exception: " + e.getMessage());
                            }
                        }
                    }

                }

                        , new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.d(TAG, "onErrorResponse: Error Occ" + error.toString());

                        TextView text = findViewById(R.id.textView);

                        if (text == null) {
                            Log.d(TAG, "onClick: NULL");
                        } else {
                            text.setText("No data was found against the search !");
                        }
                    }
                });

        vQueue.add(jsonObjectRequest);
    }
    public void postData(String url, JSONObject data) throws JSONException {
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObj = new JsonObjectRequest(Request.Method.POST, url,new JSONObject(String.valueOf(data)),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, "onResponse: Went Fine");
                        Toast.makeText(MainActivity.this,"Product was saved successfully.",Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "onErrorResponse: Problem occurred ");
                    }
                }
        ){
            //here I want to post data to sever
        };
        requestQueue.add(jsonObj);

    }
}

