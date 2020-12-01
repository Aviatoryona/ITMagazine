package com.magazineapp;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.magazineapp.http.Api;
import com.magazineapp.http.ApiClient;
import com.magazineapp.models.User;

import retrofit2.Retrofit;

public class App extends Application {
    private static App instance;
    private static Retrofit retrofit;
    private static User user;
    public static final String ENDPOINT = "http://192.168.43.159/MagazineApp/src/api.php?";
    private static RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        getRequestQueue();
        getRetrofit();
    }

    public static App getInstance() {
        return instance;
    }

    public static void setUser(User user) {
        App.user = user;
    }

    public static User getUser() {
        return App.user;
    }

    private static Retrofit getRetrofit() {
        if (retrofit == null) {
            retrofit = ApiClient.getRetrofitClient();
        }
        return retrofit;
    }

    public static Api getApi() {
        return App.getRetrofit().create(Api.class);
    }

    public static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getInstance());
        }
        return requestQueue;
    }
}
