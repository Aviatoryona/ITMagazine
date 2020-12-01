package com.magazineapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.magazineapp.http.ApiClient;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.MarginDecor;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.Advert;
import com.magazineapp.models.Story;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Advertiser extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advertiser);


        initViews();
        setSupportActionBar(toolbar);

        user = validateUser();
        toolbar.setSubtitle(user.getName());
        fab.setOnClickListener(view -> NextActivity(MakeAdvert.class));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MarginDecor(8));
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        ));

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
                Map<String, String> params = getParameters("getadvertbyuserid");
                params.put("user_id", String.valueOf(user.getId()));
                return params;
            }
        };

        App.getRequestQueue().add(stringRequest);
    }

    /**
     * @throws JSONException err
     */
    private void loadData_1() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getadvertbyuserid");
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

    private void process(@NonNull String body) {
        try {
            List<Advert> adverts = ParseJson.getAdvertList(body);
            recyclerView.setAdapter(new RecAdapter(adverts, this));
        } catch (JSONException e) {
            showMessage("", "You have not submitted adverts");
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.advertiser, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navRefresh) {
            try {
                loadData();
            } catch (JSONException e) {
                showMessage("", "Please try again");
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar pg;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        pg = findViewById(R.id.pg);
        fab = findViewById(R.id.fab);
    }

    static class VH extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTitle;
        TextView txtStatus;
        TextView txtBody;
        TextView txtDate;

        public VH(@NonNull View convertView) {
            super(convertView);
            img = convertView.findViewById(R.id.img);
            txtTitle = convertView.findViewById(R.id.txtTitle);
            txtStatus = convertView.findViewById(R.id.txtStatus);
            txtBody = convertView.findViewById(R.id.txtBody);
            txtDate = convertView.findViewById(R.id.txtDate);
        }
    }

    static class RecAdapter extends RecyclerView.Adapter<VH> {
        List<Advert> adverts;
        Context context;

        public RecAdapter(List<Advert> adverts, Context context) {
            this.adverts = adverts;
            this.context = context;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.advertitem, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Advert advert = adverts.get(holder.getAdapterPosition());
            if (advert != null) {
                Glide.with(context)
                        .load(ApiClient.IMAGE_URL + advert.getImage())
                        .into(holder.img);

                holder.txtTitle.setText(Html.fromHtml(advert.getTitle()));
                holder.txtBody.setText(Html.fromHtml(advert.getDescription()));
                String status = "Pending";
                switch (advert.getStatus()) {
                    case 1:
                        status = "Processing";
                        break;

                    case 2:
                        status = "Published";
                        break;

                    case 3:
                        status = "Declined";
                        break;

                }

//            status += advert.getPaid() == 0 ? ", Unpaid" : ", Paid";
                holder.txtStatus.setText(
                        status
                );
                holder.txtDate.setText(advert.getDate());
                holder.itemView.setOnClickListener(view -> nextActivity(advert));
            }
        }

        void nextActivity(Advert advert) {
            Intent intent = new Intent(context, AdvertDetails.class);
            intent.putExtra("DATA", advert);
            context.startActivity(intent);
        }

        @Override
        public int getItemCount() {
            return adverts.size();
        }
    }
}