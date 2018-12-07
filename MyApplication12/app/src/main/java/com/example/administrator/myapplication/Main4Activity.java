package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main4Activity extends AppCompatActivity {
    EditText editText;
    Button btnSearch;
    ListView lvSearch;
    ArrayList<Search> arraySearch;
    SearchAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        editText = (EditText) findViewById(R.id.editText);
        btnSearch = (Button) findViewById(R.id.buttontimkiem);
        lvSearch = (ListView) findViewById(R.id.listviewsearch);


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arraySearch = new ArrayList<>();
                adapter = new SearchAdapter(Main4Activity.this,R.layout.row_search,arraySearch);
                lvSearch.setAdapter(adapter);
                getJsonSearch("https://api.themoviedb.org/3/search/movie?api_key=a04329d567415d2371a88fa497271e10&query=" + editText.getText().toString().trim().replace(" ","%20"));
            }
        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Main4Activity.this, Main3Activity.class);
                intent.putExtra("idMovie", arraySearch.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void getJsonSearch(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonresults = response.getJSONArray("results");
                    String id = "";
                    String poster_path = "";
                    String title = "";
                    String release_date = "";
                    for (int i=0;i<jsonresults.length();i++){
                        JSONObject jsonresult = jsonresults.getJSONObject(i);
                        id = jsonresult.getString("id");
                        poster_path = MainActivity.imageUrl + jsonresult.getString("poster_path");
                        title = jsonresult.getString("title");
                        release_date = jsonresult.getString("release_date");
                        arraySearch.add(new Search(id, poster_path, title, release_date));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main4Activity.this,"Loi!",Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
