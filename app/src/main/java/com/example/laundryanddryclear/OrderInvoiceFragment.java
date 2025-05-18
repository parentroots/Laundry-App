package com.example.laundryanddryclear;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class OrderInvoiceFragment extends Fragment {

    ListView listView;

    ArrayList<HashMap<String,String>> arrayList=new ArrayList<>();
    HashMap<String,String>hashMap=new HashMap<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_invoice, container, false);

        listView=view.findViewById(R.id.listView);



        Adapter adapter=new Adapter();
        listView.setAdapter(adapter);


        String URL="http://192.168.0.101/apps/laundryDataRetrive.php";

        RequestQueue queue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {


                arrayList.clear();

                for (int i=0;i<response.length();i++){

                    try {

                        JSONObject jsonObject=response.getJSONObject(i);


                        Log.d("OrderData", jsonObject.toString());


                        String id=jsonObject.getString("id");
                        String category=jsonObject.getString("category");
                        String clothName=jsonObject.getString("clothName");
                        String date=jsonObject.getString("date");
                        String money=jsonObject.getString("money");
                        String quantity =jsonObject.getString("quantity");
                        String number=jsonObject.getString("number");
                        String location=jsonObject.getString("location");


                        hashMap=new HashMap<>();
                        hashMap.put("id",id);
                        hashMap.put("category",category);
                        hashMap.put("clothName",clothName);
                        hashMap.put("date",date);
                        hashMap.put("money",money);
                        hashMap.put("quantity",quantity);
                        hashMap.put("number",number);
                        hashMap.put("location",location);

                        arrayList.add(hashMap);

                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                }
                adapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.e("VolleyError", "Error: " + error.getMessage());


            }
        });


        queue.add(jsonArrayRequest);




        return view;
    }
    private class Adapter extends BaseAdapter{

        TextView invoiceIdText,invoiceDateText,numberText,locationText,categoryText,quantityText,priceText;

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View view = inflater.inflate(R.layout.invoice_list, parent,false);

            invoiceIdText=view.findViewById(R.id.invoiceIdText);
            invoiceDateText=view.findViewById(R.id.invoiceDateText);
            numberText=view.findViewById(R.id.numberText);
            locationText=view.findViewById(R.id.locationText);
            categoryText=view.findViewById(R.id.categoryText);
            quantityText=view.findViewById(R.id.quantityText);
            priceText=view.findViewById(R.id.priceText);

            HashMap<String,String> responseMap=arrayList.get(position);

            String id=responseMap.get("id");
            String category=responseMap.get("category");
            String clothName=responseMap.get("clothName");
            String date=responseMap.get("date");
            String money=responseMap.get("money");
            String quantity =responseMap.get("quantity");
            String number=responseMap.get("number");
            String location=responseMap.get("location");



            invoiceIdText.setText("Invoice ID: " + id);
            invoiceDateText.setText("Date: " + date);
            numberText.setText("Number: " + number);
            locationText.setText("Location: " + location);
            categoryText.setText("Category: " + category);
            quantityText.setText("Qty: " + quantity);
            priceText.setText("৳ " + money);
            categoryText.setText(clothName);




            return view;
        }
    }



}