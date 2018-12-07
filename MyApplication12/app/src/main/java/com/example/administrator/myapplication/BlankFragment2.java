package com.example.administrator.myapplication;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {
    /**
     * Example URL:
     * http://i1.ytimg.com/vi/TDFAYRtrYuk/hqdefault.jpg
     * For more info:
     * http://stackoverflow.com/questions/2068344/how-do-i-get-a-youtube-video-thumbnail-from-the-youtube-api
     */
    String trailerImageUrl = "http://i1.ytimg.com/vi/";
    String youtube = "https://www.youtube.com/watch?v=";

    ArrayList<Trailer> arrayTrailer;
    TrailerAdapter adapterTrailer;
    RecyclerView mRecyclerViewTrailer;
    RecyclerView.LayoutManager mLayoutManagerTrailer;

    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecyclerViewTrailer = (RecyclerView) getActivity().findViewById(R.id.recyclerViewTrailer);
        mLayoutManagerTrailer = new LinearLayoutManager(getActivity());
        mRecyclerViewTrailer.setLayoutManager(mLayoutManagerTrailer);
        arrayTrailer = new ArrayList<>();
        adapterTrailer = new TrailerAdapter(getActivity(),R.layout.trailer_item,arrayTrailer);
        mRecyclerViewTrailer.setAdapter(adapterTrailer);

        getJsonTrailer("https://api.themoviedb.org/3/movie/" + Main3Activity.id + "/videos?api_key=a04329d567415d2371a88fa497271e10");
        adapterTrailer.setItemClickListener(new TrailerAdapter.ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                Intent intent = new Intent(getActivity(),PlayVideoTrailerActivity.class);
                intent.putExtra("keyVideoTrailer", arrayTrailer.get(position).getKeyTrailer());
                startActivity(intent);
            }
        });


    }

    private void getJsonTrailer (String url){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonresults = response.getJSONArray("results");
                    String trailer_image = "";
                    String trailer_key = "";
                    for (int i = 0; i<jsonresults.length(); i++) {
                        JSONObject jsonresult = jsonresults.getJSONObject(i);
                        trailer_key = jsonresult.getString("key");
                        trailer_image = trailerImageUrl + trailer_key + "/hqdefault.jpg";
                        arrayTrailer.add(new Trailer(trailer_image,trailer_key));

                    }
                    adapterTrailer.notifyDataSetChanged();

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
