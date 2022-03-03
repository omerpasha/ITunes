package com.example.itunes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.icu.util.EthiopicCalendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private List<ListItem> listItems;
private MyAdapter adapter=null;

public static final String TAG="MyTag";
Button searchBtn;
public String editTextSearch;
private ObjectMapper mapper=new ObjectMapper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView= findViewById(R.id.recylerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(new ArrayList<>(), getApplicationContext()));

        searchBtn=findViewById(R.id.searchButton);


        final RequestQueue queue= Volley.newRequestQueue(this);
        searchBtn.setOnClickListener(v -> {
            EditText textView= findViewById(R.id.text);
            editTextSearch=textView.getText().toString();
            if(editTextSearch.isEmpty()){
                Toast.makeText(MainActivity.this,"write bla bla",Toast.LENGTH_LONG).show();
            }else{
                String url="https://itunes.apple.com/search?term="+editTextSearch;

                final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Loading data ...");
                progressDialog.show();

                StringRequest stringRequest=new StringRequest(Request.Method.GET, url, response -> {
                    progressDialog.dismiss();

                    try {
                        JsonNode list=mapper.readTree(response).get("results");
                        Map<String, List<JsonNode>> typeMap=
                                StreamSupport.stream(list.spliterator(),false)
                                        .collect(Collectors.groupingBy(result->result.get("wrapperType").asText()));
                        listItems=new ArrayList<>();

                        typeMap.forEach((type,results)->{
                            //separator
                            listItems.add(new ListItem(type,null,null,null));
                            results.forEach(result->{
                                String id=null;
                                String value=null;
                                if (result.has("trackId")){
                                    id=result.get("trackId").asText();
                                    value="track";
                                }else if (result.has("collectionId")){
                                    id=result.get("collectionId").asText();
                                    value="collection";
                                }else if (result.has("ArtistId")){
                                    id=result.get("ArtistId").asText();
                                    value="Artist";
                                }
                                listItems.add(new ListItem(result.get("wrapperType").asText(),result.get("wrapperType").asText(),id,value));
                            });
                        });

                        adapter = new MyAdapter(listItems, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    } catch (Exception e) {

                        e.printStackTrace();

                    }
                }, error -> progressDialog.dismiss());

                stringRequest.setTag(TAG);
                queue.add(stringRequest);

            }

        });


        queue.cancelAll(TAG);

    }

}

