package com.magazineapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.MarginDecor;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.Story;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Load approved stories and set paid
 */
public class Accounts extends BaseActivity {

    /**
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        initViews();
        setSupportActionBar(toolbar);
        user = validateUser();
        toolbar.setSubtitle(user.getName());
        fab.setOnClickListener(view -> {
            loadData();
        });
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new MarginDecor(8));
        recyclerView.setLayoutManager(new LinearLayoutManager(
                this,
                LinearLayoutManager.VERTICAL,
                false
        ));

        loadData();

    }

    /**
     * Load data
     */
    private void loadData() {
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
                Map<String, String> params = getParameters("getstorybystatus");
                params.put("status", "1"); // all published stories
                return params;
            }
        };

        App.getRequestQueue().add(stringRequest);
    }

    /**
     * @throws JSONException err
     */
    private void loadDataRetrofit() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "getstorybystatus");
        jsonObject.put("status", "1"); // all published stories
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
            List<Story> stories = ParseJson.getStoryList(body);
            recyclerView.setAdapter(new RecAdapter(stories, this));
        } catch (JSONException e) {
            showMessage("", "You have not submitted story");
        }
    }

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private ProgressBar pg;
    private FloatingActionButton fab;

    /**
     *
     */
    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.recyclerView);
        pg = findViewById(R.id.pg);
        fab = findViewById(R.id.fab);
    }

    /**
     *
     */
    static class VH extends RecyclerView.ViewHolder {
        TextView txtTitle;
        TextView txtStatus;
        TextView txtBody;
        TextView txtDate;

        public VH(@NonNull View convertView) {
            super(convertView);
            txtTitle = convertView.findViewById(R.id.txtTitle);
            txtStatus = convertView.findViewById(R.id.txtStatus);
            txtBody = convertView.findViewById(R.id.txtBody);
            txtDate = convertView.findViewById(R.id.txtDate);
        }
    }

    /**
     *
     */
    static class RecAdapter extends RecyclerView.Adapter<VH> {
        List<Story> stories;
        Context context;

        public RecAdapter(List<Story> stories, Context context) {
            this.stories = stories;
            this.context = context;
        }

        @NonNull
        @Override
        public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.story_item, parent, false);
            return new VH(view);
        }

        @Override
        public void onBindViewHolder(@NonNull VH holder, int position) {
            Story advert = stories.get(holder.getAdapterPosition());
            if (advert != null) {

                holder.txtTitle.setText(Html.fromHtml(advert.getTitle()));
                holder.txtBody.setText(Html.fromHtml(advert.getDesc()));
                String status = "Pending";
                switch (advert.getStatus()) {
                    case 1:
                        status = "published";
                        break;

                    case 2:
                        status = "Declined";
                        break;
                }

                status += advert.getPaid() == 0 ? ", Unpaid" : ", Paid";
                holder.txtStatus.setText(
                        status
                );
                holder.txtDate.setText(advert.getDate());
                holder.itemView.setOnClickListener(view -> nextActivity(advert));
            }
        }

        void nextActivity(Story story) {
            Intent intent = new Intent(context, StoryDetails.class);
            intent.putExtra("DATA", story);
            context.startActivity(intent);
        }

        @Override
        public int getItemCount() {
            return stories.size();
        }
    }

}