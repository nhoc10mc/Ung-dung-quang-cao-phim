package com.example.administrator.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Main5Activity extends AppCompatActivity {
    ImageView imgPerson;
    TextView txtNamePerson, txtBirthdayPerson, txtFlaceOfBirth, txtBiographyPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        imgPerson = (ImageView) findViewById(R.id.imgperson);
        txtNamePerson =(TextView) findViewById(R.id.txtnameperson);
        txtBirthdayPerson = (TextView) findViewById(R.id.txtbirthdayperson);
        txtFlaceOfBirth = (TextView) findViewById(R.id.txtflaceofbirth);
        txtBiographyPerson = (TextView) findViewById(R.id.txtbiography);


        Intent intent = getIntent();
        String idPerson = intent.getStringExtra("Castid");

        getJsonPerson("https://api.themoviedb.org/3/person/" + idPerson + "?api_key=a04329d567415d2371a88fa497271e10");

    }

    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
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
            imgPerson.setImageBitmap(bitmap);
        }
    }

    private void getJsonPerson(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    new LoadImage().execute("https://image.tmdb.org/t/p/w500"+ response.getString("profile_path"));
                    txtBirthdayPerson.setText(response.getString("birthday"));
                    txtNamePerson.setText(response.getString("name"));
                    txtBiographyPerson.setText(response.getString("biography"));
                    txtFlaceOfBirth.setText(response.getString("place_of_birth"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Main5Activity.this, "Loi!", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue.add(jsonObjectRequest);
    }


}
