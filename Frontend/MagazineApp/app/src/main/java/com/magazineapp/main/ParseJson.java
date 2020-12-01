package com.magazineapp.main;

import androidx.annotation.NonNull;

import com.magazineapp.models.Advert;
import com.magazineapp.models.MessageModel;
import com.magazineapp.models.Photo;
import com.magazineapp.models.Story;
import com.magazineapp.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParseJson {

    /**
     *
     * @param json
     * @return
     */
    public  static MessageModel getMessageModel(String json){
        try {
            JSONObject jsonObject=new JSONObject(json);
            return new MessageModel(
                    jsonObject.getBoolean("error"),
                    jsonObject.getString("message"),
                    jsonObject.get("data")
            );
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * @param json
     * @return
     */
    public static User getUser(@NonNull String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            return new User(
                    jsonObject.getInt("id"),
                    jsonObject.getString("name"),
                    jsonObject.getString("email"),
                    jsonObject.getString("category"),
                    jsonObject.getString("pwd"),
                    jsonObject.getString("date")
            );
        } catch (JSONException e) {
            LogE(e);
        }
        return null;
    }


    /**
     * @param e
     */
    public static void LogE(Exception e) {
        Logger.getLogger(ParseJson.class.getSimpleName()).log(Level.SEVERE, e.toString());
    }

    /**
     * @param json String
     * @return List<Advert>
     */
    public static List<Advert> getAdvertList(@NonNull String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Advert> adverts = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Advert advert = new Advert(
                    jsonObject.getInt("id"),
                    jsonObject.getInt("user_id"),
                    jsonObject.getInt("status"),
                    jsonObject.getString("title"),
                    jsonObject.getString("desc"),
                    jsonObject.getString("img"),
                    jsonObject.getString("date"),
                    jsonObject.getInt("paid"),
                    jsonObject.getString("position")
            );
            adverts.add(advert);
        }
        return adverts;
    }


    /**
     * @param json String
     * @return List<Story>
     * @throws JSONException error
     */
    public static List<Story> getStoryList(@NonNull String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Story> stories = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Story story =
                    new Story(jsonObject.getInt("id"),
                            jsonObject.getInt("status"),
                            jsonObject.getInt("paid"),
                            jsonObject.getInt("user_id"),
                            jsonObject.getString("title"),
                            jsonObject.getString("desc"),
                            jsonObject.getString("photoids"),
                            jsonObject.getString("date")
                    );

            stories.add(story);
        }
        return stories;
    }

    /**
     * @param json
     * @return
     * @throws JSONException
     */
    public static List<Photo> getPhotoList(@NonNull String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        List<Photo> photos = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Photo photo =
                    new Photo(jsonObject.getInt("id"),
                            jsonObject.getInt("status"),
                            jsonObject.getInt("user_id"),
                            jsonObject.getString("desc"),
                            jsonObject.getString("url"),
                            jsonObject.getString("date")
                    );
            photos.add(photo);
        }
        return photos;
    }

}
