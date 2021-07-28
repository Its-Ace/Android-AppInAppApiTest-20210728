package com.example.productget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import static android.content.ContentValues.TAG;

public class addProductDailog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder box  = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View content = inflater.inflate(R.layout.add_product_dailog,null);//View object is there for us now we can get the data from this object

        Button submitButton = content.findViewById(R.id.submitButton);
        EditText pPrice = content.findViewById(R.id.productPrice);
        EditText pName = content.findViewById(R.id.productName);
        EditText pCat = content.findViewById(R.id.productCategory);
        EditText pSup = content.findViewById(R.id.supplierId);

        Log.d(TAG, "onCreateDialog: "+pSup.getText().toString());
        box.setView(content);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pPrice.getText().toString().equals("") || pName.getText().toString().equals("") || pSup.getText().toString().equals("") || pCat.getText().toString().equals("")) {
                    content.findViewById(R.id.on_error_text).setVisibility(View.VISIBLE);
                    Log.d(TAG, "onClick: Everything went fine");
                } else {
                    content.findViewById(R.id.on_error_text).setVisibility(View.INVISIBLE);

                    String url = "http://10.0.2.2:5000/api/products/";

                    JSONObject data = null;

                    data = new JSONObject();
                    try {

                        //data.put("productId",15);
                        data.put("name",pName.getText().toString());
                        data.put("price",Double.parseDouble(pPrice.getText().toString()));
                        data.put("categoryId",Integer.parseInt(pCat.getText().toString()));
                        data.put("supplierId",Integer.parseInt(pSup.getText().toString()));

                        Log.d(TAG, "onClick: " + data.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    try {

                        ((MainActivity) requireActivity()).postData(url,data);
                        dismiss();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    Log.d(TAG, "onClick: Everything went fine"+getActivity().getClass().getName());

                }
            }
        });
        box.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getContext(),"Cancel was clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return box.create();
    }
}
