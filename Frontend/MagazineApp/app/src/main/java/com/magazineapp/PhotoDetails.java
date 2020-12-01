package com.magazineapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.magazineapp.http.ApiClient;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.models.Photo;

public class PhotoDetails extends BaseActivity {
    Photo photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = validateUser();
        setContentView(R.layout.activity_photo_details);

        initViews();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        if (getIntent().hasExtra("DATA")) {
            photo = (Photo) getIntent().getSerializableExtra("DATA");
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
        if (photo != null) {
            Glide.with(this)
                    .load(ApiClient.IMAGE_URL + photo.getUrl())
                    .into(img);
            edtDesc.setText(photo.getDesc());
        }
    }

    private Toolbar toolbar;
    private TextView edtDesc;
    private ImageView img;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        edtDesc = findViewById(R.id.edtDesc);
        img = findViewById(R.id.img);
    }

}