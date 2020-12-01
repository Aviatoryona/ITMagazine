package com.magazineapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.MessageModel;
import com.magazineapp.models.Story;

import java.util.Map;

public class StoryDetails extends BaseActivity {

    Story story;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = validateUser();
        setContentView(R.layout.activity_story_details);


        initViews();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        if (getIntent().hasExtra("DATA")) {
            story = (Story) getIntent().getSerializableExtra("DATA");
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
        if (story != null) {

            txtTitle.setText(Html.fromHtml(story.getTitle()));
            txtBody.setText(Html.fromHtml(story.getDesc()));
            String status = "Pending";
            switch (story.getStatus()) {
                case 1:
                    status = "published";
                    break;

                case 2:
                    status = "Declined";
                    break;

            }

            status += story.getPaid() == 0 ? ", Unpaid" : ", Paid";
            txtStatus.setText(
                    status
            );
            txtDate.setText(story.getDate());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (user.getCategory().equalsIgnoreCase("1") || user.getCategory().equalsIgnoreCase("3")) {
            getMenuInflater().inflate(R.menu.story_details, menu);
            if (user.getCategory().equalsIgnoreCase("1")) {//editor
                menu.findItem(R.id.navPay).setVisible(false);
            }
            if (user.getCategory().equalsIgnoreCase("3")) { //Accounts
                menu.findItem(R.id.navPay).setVisible(true);
                menu.findItem(R.id.navApprove).setVisible(false);
                menu.findItem(R.id.navDecline).setVisible(false);
            }
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.navApprove) {
            updateStory(1, story.getPaid());
        }
        if (item.getItemId() == R.id.navDecline) {
            updateStory(2, story.getPaid());
        }
        if (item.getItemId() == R.id.navPay) {
            updateStory(story.getPaid(), 1);
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateStory(int status, int paid) {
        story.setStatus(status);
        story.setPaid(paid);
        ProgressDialog progressDialog = getProgressDialog("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.ENDPOINT, response -> {
            progressDialog.dismiss();

            MessageModel messageModel = ParseJson.getMessageModel(response);
            if (messageModel == null) {
                showMessage("", "Please try again");
                return;
            }

            showMessage("", "Story has been updated");

        }, error -> {

            progressDialog.dismiss();
            showMessage("Sorry", "Please try again");

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> stringStringMap = getParameters("updatestory");
                stringStringMap.put("id", String.valueOf(story.getId()));
                stringStringMap.put("status", String.valueOf(story.getStatus()));
                stringStringMap.put("paid", String.valueOf(story.getPaid()));
                stringStringMap.put("user_id", String.valueOf(story.getUser_id()));
                stringStringMap.put("title", String.valueOf(story.getTitle()));
                stringStringMap.put("desc", String.valueOf(story.getDesc()));
                stringStringMap.put("photoids", String.valueOf(story.getPhotoIds()));
                return stringStringMap;
            }
        };
        App.getRequestQueue().add(stringRequest);
    }

    private Toolbar toolbar;
    private TextView txtTitle;
    private TextView txtStatus;
    private TextView txtBody;
    private TextView txtDate;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        txtTitle = findViewById(R.id.txtTitle);
        txtStatus = findViewById(R.id.txtStatus);
        txtBody = findViewById(R.id.txtBody);
        txtDate = findViewById(R.id.txtDate);
    }
}