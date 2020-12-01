package com.magazineapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.MessageModel;

import org.json.JSONException;

import java.util.Map;

public class CreateStory extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = validateUser();
        setContentView(R.layout.activity_create_story);

        initViews();
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(view -> finish());

        fab.setOnClickListener(view -> {
            try {
                submit();
            } catch (JSONException e) {
                showMessage("", "Please try again");
            }
        });
    }

    private void submit() throws JSONException {
        if (TextUtils.isEmpty(edtTitle.getText())) {
            edtTitle.setError("*");
            return;
        }
        if (TextUtils.isEmpty(edtBody.getText())) {
            edtBody.setError("*");
            return;
        }

        if (TextUtils.isEmpty(edtTitle.getText())) {
            edtTitle.setError("*");
            return;
        }

        if (TextUtils.isEmpty(edtBody.getText())) {
            edtBody.setError("*");
            return;
        }


        ProgressDialog pg = getProgressDialog("Please wait...");
        pg.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.ENDPOINT, response -> {
            pg.dismiss();
            MessageModel messageModel = ParseJson.getMessageModel(response);
            if (messageModel == null) {
                showMessage("Sorry", "Please try again");
                return;
            }
            if (messageModel.isError()) {
                showMessage("", messageModel.getMessage());
            } else {
                showMessage("", "Your story has been submitted");
            }
        }, error -> {
            pg.dismiss();
            showMessage("Sorry", "Please try again");
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = getParameters("addstory");
                params.put("title", edtTitle.getText().toString());
                params.put("desc", edtBody.getText().toString());
                params.put("user_id", String.valueOf(user.getId()));
                return params;
            }
        };

        App.getRequestQueue().add(stringRequest);

//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("action", "addstory");
//        Call<MessageModel> call = App.getApi().makeCallRequest(jsonObject.toString());
//        call.enqueue(new Callback<MessageModel>() {
//            @Override
//            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
//                pg.dismiss();
//                if (response.body() != null) {
//                    MessageModel messageModel = response.body();
//                    if (messageModel.isError()) {
//                        showMessage("", messageModel.getMessage());
//                    } else {
//                        showMessage("", "Your story has been submitted");
//                    }
//                    return;
//                }
//
//                showMessage("Sorry", "Please try again");
//            }
//
//            @Override
//            public void onFailure(Call<MessageModel> call, Throwable t) {
//                pg.dismiss();
//                showMessage("Sorry", "Please try again");
//            }
//        });
    }

    private Toolbar toolbar;
    private EditText edtTitle;
    private EditText edtBody;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        edtTitle = findViewById(R.id.edtTitle);
        edtBody = findViewById(R.id.edtBody);
        fab = findViewById(R.id.fab);
    }

}