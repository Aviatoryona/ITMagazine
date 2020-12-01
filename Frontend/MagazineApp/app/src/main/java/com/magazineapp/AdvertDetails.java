package com.magazineapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.magazineapp.http.ApiClient;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.Advert;
import com.magazineapp.models.MessageModel;

import java.util.Map;

public class AdvertDetails extends BaseActivity {

    Advert advert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = validateUser();
        setContentView(R.layout.activity_advert_details);

        initViews();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        if (getIntent().hasExtra("DATA")) {
            advert = (Advert) getIntent().getSerializableExtra("DATA");
        } else {
            showMessage("Sorry", "Unable to load data");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindData();
    }

    private void bindData() {
        if (advert != null) {
            Glide.with(this)
                    .load(ApiClient.IMAGE_URL + advert.getImage())
                    .into(img);

            txtTitle.setText(Html.fromHtml(advert.getTitle()));
            txtBody.setText(Html.fromHtml(advert.getDescription()));
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
            txtStatus.setText(
                    status
            );
            txtDate.setText(advert.getDate());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getCategory().equalsIgnoreCase("0") || user.getCategory().equalsIgnoreCase("2")) //editor
            getMenuInflater().inflate(R.menu.advert_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navApprove) {
            updateAdvert(user.getCategory().equalsIgnoreCase("0") ? 1 : 2);
        }
        if (item.getItemId() == R.id.navDecline) {
            updateAdvert(3);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateAdvert(int status) {
        advert.setStatus(status);
        ProgressDialog progressDialog = getProgressDialog("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.ENDPOINT, response -> {
            progressDialog.dismiss();

            MessageModel messageModel = ParseJson.getMessageModel(response);
            if (messageModel == null) {
                showMessage("", "Please try again");
                return;
            }

            showMessage("", "Advert has been updated");

        }, error -> {
            progressDialog.dismiss();
            showMessage("Sorry", "Please try again");
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> stringStringMap = getParameters("updateadvert");
                stringStringMap.put("id", String.valueOf(advert.getId()));
                stringStringMap.put("status", String.valueOf(advert.getStatus()));
                stringStringMap.put("paid", String.valueOf(advert.getPaid()));
                stringStringMap.put("user_id", String.valueOf(advert.getUser_id()));
                stringStringMap.put("title", String.valueOf(advert.getTitle()));
                stringStringMap.put("desc", String.valueOf(advert.getDescription()));
                stringStringMap.put("position", String.valueOf(advert.getPosition()));
                return stringStringMap;
            }
        };
        App.getRequestQueue().add(stringRequest);
    }

    private Toolbar toolbar;
    private ImageView img;
    private TextView txtTitle;
    private TextView txtStatus;
    private TextView txtBody;
    private TextView txtDate;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        img = findViewById(R.id.img);
        txtTitle = findViewById(R.id.txtTitle);
        txtStatus = findViewById(R.id.txtStatus);
        txtBody = findViewById(R.id.txtBody);
        txtDate = findViewById(R.id.txtDate);
    }

}