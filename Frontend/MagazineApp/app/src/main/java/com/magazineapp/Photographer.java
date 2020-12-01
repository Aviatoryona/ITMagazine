package com.magazineapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.magazineapp.http.ApiClient;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.Photo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Photographer extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photographer);
        user = validateUser();
        initViews();
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(user.getName());

        toolbar.setSubtitle(user.getName());
        fab.setOnClickListener(view -> {
            try {
                loadData();
            } catch (JSONException e) {
                showMessage("", "Please try again");
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        try {
            loadData();
        } catch (JSONException e) {
            showMessage("", "Please try again");
        }
    }

    /**
     * Load data
     */
    private void loadData() throws JSONException {
        pg.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.ENDPOINT, response -> {
            pg.setVisibility(View.GONE);
            process(response);
        }, error -> {
            pg.setVisibility(View.GONE);
            showMessage("Sorry", "Please try again");
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = getParameters("getphotosbyuserid");
                params.put("user_id", String.valueOf(user.getId()));
                return params;
            }
        };

        App.getRequestQueue().add(stringRequest);
    }

    /**
     * Load data
     *
     * @throws JSONException error
     */
    private void loadData_1() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getphotosbyuserid");
        jsonObject.put("user_id", String.valueOf(user.getId()));

        pg.setVisibility(View.VISIBLE);
        Call<String> call = App.getApi().makeRequest(jsonObject.toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                pg.setVisibility(View.GONE);
                if (response.body() != null) {
                    process(response.body());
                    return;
                }
                showMessage("", "Please try again");
            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                pg.setVisibility(View.GONE);
                showMessage("Sorry", "Please try again");
            }
        });
    }

    /**
     * @param body String
     */
    private void process(@NonNull String body) {
        try {
            List<Photo> photos = ParseJson.getPhotoList(body);
            gridView.setAdapter(new GridAdapter(photos, this));
        } catch (JSONException e) {
            showMessage("", "You have not submitted story");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.photographer, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navAdd) {
            NextActivity(UploadPhotos.class);
        }
        return super.onOptionsItemSelected(item);
    }

    private Toolbar toolbar;
    private GridView gridView;
    private ProgressBar pg;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        gridView = findViewById(R.id.gridView);
        pg = findViewById(R.id.pg);
        fab = findViewById(R.id.fab);
    }


    static class GridAdapter extends BaseAdapter {
        List<Photo> photos;
        Context context;

        public GridAdapter(List<Photo> photos, Context context) {
            this.photos = photos;
            this.context = context;
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public Object getItem(int i) {
            return photos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.photoitem, viewGroup, false);
            }
            Photo photo = (Photo) getItem(i);
            ImageView img = view.findViewById(R.id.img);
            Glide.with(context)
                    .load(ApiClient.IMAGE_URL + photo.getUrl())
                    .into(img);
            view.setOnClickListener(view1 -> nextActivity(photo));
            return view;
        }

        void nextActivity(Photo photo) {
            Intent intent = new Intent(context, PhotoDetails.class);
            intent.putExtra("DATA", photo);
            context.startActivity(intent);
        }

    }
}