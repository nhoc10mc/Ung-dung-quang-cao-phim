package com.example.administrator.myapplication;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment3 extends Fragment implements Callback<String>{

    EditText edt;
    Button btn;
    ListView lvComment;
    ArrayList<Comment> arrayCommnet;
    CommentAdapter adapter;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IMyService iMyService;

    @Override
    public void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    public BlankFragment3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_blank_fragment3, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Init Service
        Retrofit retrofitClient = RetrofitClient.getInstance();
        iMyService = retrofitClient.create(IMyService.class);

        //Init View
        edt = (EditText) getActivity().findViewById(R.id.edtComment);
        btn = (Button) getActivity().findViewById(R.id.btnComment);
        lvComment = (ListView) getActivity().findViewById(R.id.lvCommnet);

        arrayCommnet = new ArrayList<>();
        adapter = new CommentAdapter(getActivity(),R.layout.layout_item_comment,arrayCommnet);
        lvComment.setAdapter(adapter);
        getJsonComment(MainActivity.commentUrl + "comments/" + Main3Activity.id);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postcomment(edt.getText().toString(),Main3Activity.id,Main3Activity.titles,Main3Activity.overview);
                arrayCommnet = new ArrayList<>();
                adapter = new CommentAdapter(getActivity(),R.layout.layout_item_comment,arrayCommnet);
                lvComment.setAdapter(adapter);
                getJsonComment(MainActivity.commentUrl + "comments/" + Main3Activity.id);
            }
        });
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {

    }
    private void postcomment(String commentcontent, String Movieid, String Movietitle, String Movieoverview){
        try{
            JSONObject paramObject = new JSONObject();
            paramObject.put("commentcontent", commentcontent);
            paramObject.put("movieId", Movieid);
            paramObject.put("movieName",Movietitle);
            paramObject.put("movieDescription",Movieoverview);
            //paramObject.put("userID", userID);
            Call<String> userCall = iMyService.getUser(paramObject.toString());
            userCall.enqueue(this);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    /*private void postkinduser(String name){
        compositeDisposable.add(iMyService.namekinduser(name)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Toast.makeText(getActivity(), ""+s,Toast.LENGTH_SHORT).show();
            }
        }));
    }*/
    private void getJsonComment(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new com.android.volley.Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray massages = response.getJSONArray("message");
                    String commentcontent;
                    String commentdate;
                    String userid;
                    for(int i =0 ;i < massages.length(); i++){
                        JSONObject massage = massages.getJSONObject(i);
                        commentcontent = massage.getString("commentcontent");
                        commentdate = massage.getString("commentdate");

                        arrayCommnet.add(new Comment(commentcontent,commentdate));
                    }
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), "Loi!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
