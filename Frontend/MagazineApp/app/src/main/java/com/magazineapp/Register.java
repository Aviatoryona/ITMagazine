package com.magazineapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.MessageModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initViews();
        btnRegister.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtName.getText())) {
                edtName.setError("*");
                return;
            }
            if (TextUtils.isEmpty(edtEmail.getText())) {
                edtEmail.setError("*");
                return;
            }
            if (TextUtils.isEmpty(edtPass.getText())) {
                edtPass.setError("*");
                return;
            }

            if (edtCategories.getSelectedItemPosition() == 0) {
                Toast.makeText(Register.this, "Select category", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                doRegister();
            } catch (JSONException e) {
                showMessage("", "Error encountered");
            }
        });
    }

    private void doRegister() throws JSONException {

        String category;
        switch (edtCategories.getSelectedItemPosition()) {
            case 1:
                category = "4";
                break;
            case 2:
                category = "5";
                break;
            case 3:
                category = "6";
                break;
            default:
                category = "-1";
        }

        ProgressDialog pg = getProgressDialog("Please wait...");
        pg.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.ENDPOINT, response -> {
            pg.dismiss();
            MessageModel messageModel = ParseJson.getMessageModel(response);
            if (messageModel != null) {
                if (!messageModel.isError()) {
                    Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                    finish();
                    NextActivity(Login.class);
                    return;
                }
            }
            showMessage("", "Please try again");
        }, error -> {
            pg.dismiss();
            showMessage("", "Please try again");
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> jsonObject = getParameters("register");
                jsonObject.put("name", String.valueOf(edtName.getText()));
                jsonObject.put("email", String.valueOf(edtEmail.getText()));
                jsonObject.put("pwd", String.valueOf(edtPass.getText()));
                jsonObject.put("category", category);
                return jsonObject;
            }
        };
        App.getRequestQueue().add(stringRequest);
    }

    private void doRegister_1() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("action", "register");
        jsonObject.put("name", edtName.getText());
        jsonObject.put("email", edtEmail.getText());
        jsonObject.put("pwd", edtPass.getText());
        String category;
        switch (edtCategories.getSelectedItemPosition()) {
            case 1:
                category = "4";
                break;
            case 2:
                category = "5";
                break;
            case 3:
                category = "6";
                break;
            default:
                category = "-1";
        }
        jsonObject.put("category", category);

        ProgressDialog pg = getProgressDialog("Please wait...");
        pg.show();

        Call<MessageModel> call = App.getApi().makeCallRequest(jsonObject.toString());
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                pg.dismiss();
                MessageModel messageModel = response.body();
                if (messageModel != null) {
                    if (!messageModel.isError()) {
                        Toast.makeText(Register.this, "Registration successful", Toast.LENGTH_SHORT).show();
                        finish();
                        NextActivity(Login.class);
                        return;
                    }
                }
                showMessage("", "Please try again");
            }

            @Override
            public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                pg.dismiss();
                showMessage("", "Please try again");
            }
        });
    }

    private TextInputEditText edtName;
    private TextInputEditText edtEmail;
    private TextInputEditText edtPass;
    private Spinner edtCategories;
    private TextView btnRegister;

    public void initViews() {
        edtName = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        edtCategories = findViewById(R.id.edtCategories);
        btnRegister = findViewById(R.id.btnRegister);
    }

}