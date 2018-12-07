package com.example.administrator.myapplication;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    ImageView imgHinh;
    TextView txtTitle, txtReleasedate, txtOverview;
    ArrayList<Cast> arrayCast;
    CastAdapter adapter;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;





    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imgHinh = (ImageView) getActivity().findViewById(R.id.imgBackdrop);
        txtTitle = (TextView) getActivity().findViewById(R.id.textViewTitle);
        txtReleasedate = (TextView) getActivity().findViewById(R.id.txtReleasedate);
        txtOverview = (TextView) getActivity().findViewById(R.id.txtOverview);
        mRecyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);

        /*new LoadImage().execute("https://image.tmdb.org/t/p/w500"+ Main3Activity.backdrop_path);
        txtTitle.setText(Main3Activity.titles);
        txtReleasedate.setText(Main3Activity.release_date);
        txtOverview.setText(Main3Activity.overview);*/
        getJsonMoviedb("https://api.themoviedb.org/3/movie/" + Main3Activity.id + "?api_key=" + MainActivity.API_KEY);



        mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL,false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setLayoutManager(mLayoutManager);
        arrayCast = new ArrayList<>();
        adapter = new CastAdapter(getActivity(),R.layout.layout_item,arrayCast);
        mRecyclerView.setAdapter(adapter);

        getJsonCast("https://api.themoviedb.org/3/movie/" + Main3Activity.id + "/credits?api_key=" + MainActivity.API_KEY);
        adapter.setOnItemClickListener(new CastAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Intent intent = new Intent(getActivity(),Main5Activity.class);
                intent.putExtra("Castid", arrayCast.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap>{
        Bitmap bitmapHinh = null;
        @Override
        protected Bitmap doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                InputStream inputStream = url.openConnection().getInputStream();
                bitmapHinh = BitmapFactory.decodeStream(inputStream);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmapHinh;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imgHinh.setImageBitmap(bitmap);
        }
    }

    private void getJsonMoviedb (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    new LoadImage().execute("https://image.tmdb.org/t/p/w500"+ response.getString("backdrop_path"));

                    txtOverview.setText(response.getString("overview"));

                    txtReleasedate.setText(response.getString("release_date"));

                    txtTitle.setText(response.getString("title"));
                    //Toast.makeText(Main3Activity.this,backdrop_path,Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Loi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }

    private void getJsonCast (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsoncasts = response.getJSONArray("cast");
                    String profile_path = "";
                    String cast_id = "";
                    for(int i = 0; i<5; i++) {
                        JSONObject jsoncast = jsoncasts.getJSONObject(i);
                        profile_path = MainActivity.imageUrl + jsoncast.getString("profile_path");
                        cast_id = jsoncast.getString("id");
                        arrayCast.add(new Cast(profile_path,cast_id));
                        //Toast.makeText(getActivity(), profile_path, Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(getActivity(), "Loi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);

    }


}
