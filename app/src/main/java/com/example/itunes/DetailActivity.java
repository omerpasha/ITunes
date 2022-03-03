package com.example.itunes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DetailActivity extends AppCompatActivity {
    TextView tv;
    ObjectMapper mapper=new ObjectMapper();
    public static final String TAG="DetailTag";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String value =intent.getStringExtra("value");
        tv=findViewById(R.id.detailText);

        showDetails(id,value);

    }

    private void showDetails(String id, String value) {
        final RequestQueue queue= Volley.newRequestQueue(this);
        try{
            String url=null;
            if (value.equalsIgnoreCase("track")){
                url="https://itunes.apple.com/search?term="+id;//burası ayarlanacak
            }else if (value.equalsIgnoreCase("collection")){
                url="https://itunes.apple.com/search?term="+id;
            }else if (value.equalsIgnoreCase("Artist")){
                url="https://itunes.apple.com/lookup?id="+id;
            }
            StringRequest stringRequest=new StringRequest(Request.Method.GET, url, response -> {
                try {
                    JsonNode results=mapper.readTree(response).get("results");
                    tv.setText(results.get(0).get("collectionName").asText());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, error -> System.out.println("error at:>"+id));
            stringRequest.setTag(TAG);
            queue.add(stringRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
