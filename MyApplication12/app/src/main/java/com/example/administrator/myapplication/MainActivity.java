package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
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

public class MainActivity extends AppCompatActivity {
    public static String commentUrl = "http://192.168.1.2:3000/";
    public static String imageUrl = "https://image.tmdb.org/t/p/w500";
    public static String API_KEY = "a04329d567415d2371a88fa497271e10";
    String urlGetJson ="https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY;
    ListView lvMovie;
    ArrayList<Movie> arrayMovie;
    MovieAdapter adapter;
    FloatingActionButton fab;
    //FragmentManager fragmentManager = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        lvMovie = (ListView) findViewById(R.id.listviewMovie);
        arrayMovie = new ArrayList<>();

        adapter = new MovieAdapter(this,R.layout.row_movie,arrayMovie);
        lvMovie.setAdapter(adapter);

        getJsonThemoviedb(urlGetJson);


        lvMovie.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                intent.putExtra("idMovie", arrayMovie.get(position).getId());
                intent.putExtra("titleMovie", arrayMovie.get(position).getTitle());
                intent.putExtra("overviewMovie", arrayMovie.get(position).getOverview());
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.add(R.id.framelayout,new TimKiemFragment());
                //fragmentTransaction.addToBackStack("aaa");
                //fragmentTransaction.commit();
                //Toast.makeText(MainActivity.this,"abc",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,Main4Activity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getBackStackEntryCount()>0){
            getSupportFragmentManager().popBackStack();
        }else {
            super.onBackPressed();
        }

    }

    private void getJsonThemoviedb (String url){

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonresults = response.getJSONArray("results");
                        String poster_path = "";
                        String movie_id = "";
                        String movie_title;
                        String movie_overview;
                        for (int i = 0; i<jsonresults.length(); i++) {
                            JSONObject jsonresult = jsonresults.getJSONObject(i);
                            poster_path = imageUrl + jsonresult.getString("poster_path");
                            movie_id = jsonresult.getString("id");
                            movie_title = jsonresult.getString("title");
                            movie_overview = jsonresult.getString("overview");
                            arrayMovie.add(new Movie(poster_path,movie_id,movie_title,movie_overview));
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
                            Toast.makeText(MainActivity.this, "Loi!", Toast.LENGTH_SHORT).show();
                        }
                    }
            );
            requestQueue.add(jsonObjectRequest);

    }
}
