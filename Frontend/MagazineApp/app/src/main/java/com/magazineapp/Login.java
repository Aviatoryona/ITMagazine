package com.magazineapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;
import com.magazineapp.http.Api;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.MessageModel;
import com.magazineapp.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();

        btnRegister.setOnClickListener(view -> {
            if (TextUtils.isEmpty(edtEmail.getText())) {
                edtEmail.setError("*");
                return;
            }
            if (TextUtils.isEmpty(edtPass.getText())) {
                edtPass.setError("*");
                return;
            }

            doLogin();
        });

    }

    /**
     *
     */
    private void doLogin() {
        ProgressDialog progressDialog = getProgressDialog("Please wait...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, App.ENDPOINT, response -> {
            progressDialog.dismiss();
            LogE(response);
            MessageModel messageModel = ParseJson.getMessageModel(response);
            if (messageModel == null) {
                showMessage("", "Please try again");
                return;
            }
            if (messageModel.isError()) {
                showMessage("", messageModel.getMessage());
                return;
            }

            String data = messageModel.getData().toString();
            User user = ParseJson.getUser(data);
            if (user != null) {
                App.setUser(user);
                switch (user.getCategory()) {
                    case "0":
                        NextActivity(Marketing.class);
                        break;
                    case "1":
                        NextActivity(Editors.class);
                        break;
                    case "2":
                        NextActivity(Processors.class);
                        break;
                    case "3":
                        NextActivity(Accounts.class);
                        break;
                    case "4":
                        NextActivity(Advertiser.class);
                        break;
                    case "5":
                        NextActivity(Journalist.class);
                        break;
                    case "6":
                        NextActivity(Photographer.class);
                        break;
                }
                finish();
                return;
            }
            showMessage("", "Error encountered, please try again");
        }, error -> {
            progressDialog.dismiss();
            showMessage("Error", "please try again");
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = getParameters("auth");
                params.put("email", String.valueOf(edtEmail.getText()));
                params.put("pwd", String.valueOf(edtPass.getText()));
                return params;
            }
        };

        App.getRequestQueue().add(stringRequest);
    }

    /**
     *
     */
    private void doLoginRetrofit() {
        ProgressDialog progressDialog = getProgressDialog("Please wait...");
        progressDialog.show();
        Api api = App.getApi();
        JSONObject object = new JSONObject();

        try {
            object.put("action", "auth");
            object.put("email", edtEmail.getText());
            object.put("pwd", edtPass.getText());
            Call<MessageModel> call = api.makeCallRequest(object.toString());
            call.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(@NonNull Call<MessageModel> call, @NonNull Response<MessageModel> response) {
                    progressDialog.dismiss();
                    LogE(response.raw().toString());
                    if (response.isSuccessful()) {
                        MessageModel messageModel = response.body();
                        if (messageModel == null) {
                            showMessage("", "Please try again");
                            return;
                        }
                        if (messageModel.isError()) {
                            showMessage("", messageModel.getMessage());
                            return;
                        }

                        String data = messageModel.getData().toString();
                        User user = ParseJson.getUser(data);
                        if (user != null) {
                            App.setUser(user);
                            switch (user.getCategory()) {
                                case "0":
                                    NextActivity(Marketing.class);
                                    break;
                                case "1":
                                    NextActivity(Editors.class);
                                    break;
                                case "2":
                                    NextActivity(Processors.class);
                                    break;
                                case "3":
                                    NextActivity(Accounts.class);
                                    break;
                                case "4":
                                    NextActivity(Advertiser.class);
                                    break;
                                case "5":
                                    NextActivity(Journalist.class);
                                    break;
                                case "6":
                                    NextActivity(Photographer.class);
                                    break;
                            }
                            finish();
                            return;
                        }
                        showMessage("", "Error encountered, please try again");
                        return;
                    }
                    showMessage("Sorry", response.message());
                }

                @Override
                public void onFailure(@NonNull Call<MessageModel> call, @NonNull Throwable t) {
                    progressDialog.dismiss();
                    t.printStackTrace();
                    Toast.makeText(Login.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (JSONException e) {
            progressDialog.dismiss();
            LogE(object.toString());
            showMessage("Error", "please try again");
        }
    }

    private TextInputEditText edtEmail;
    private TextInputEditText edtPass;
    private TextView btnRegister;

    public void initViews() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        btnRegister = findViewById(R.id.btnRegister);
    }

}