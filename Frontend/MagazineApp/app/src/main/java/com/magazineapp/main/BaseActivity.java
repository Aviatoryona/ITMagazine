package com.magazineapp.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Base64;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.magazineapp.App;
import com.magazineapp.Login;
import com.magazineapp.models.User;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BaseActivity extends AppCompatActivity {
    public User user;

    /**
     * @return User
     */
    public User validateUser() {
        User user = App.getUser();
        if (user == null) {
            finish();
            NextActivity(Login.class);
            return null;
        }
        return user;
    }

    /**
     * @param title title
     * @param msg   msg
     */
    public void showMessage(String title, String msg) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(msg)
                .setNeutralButton("Dismiss", (dialogInterface, i) -> dialogInterface.dismiss())
                .show();
    }

    /**
     * @param msg msg
     * @return progressDialog
     */
    public ProgressDialog getProgressDialog(String msg) {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        return progressDialog;
    }

    /**
     * @param c Class
     */
    public void NextActivity(Class c) {
        startActivity(new Intent(this, c));
    }

    /**
     * @param bitmap image
     * @return String
     */
    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        // Get the Base64 string
        return Base64.encodeToString(byteFormat, Base64.NO_WRAP);
    }

    /**
     * @param e
     */
    public void LogE(String e) {
        Logger.getLogger(BaseActivity.class.getSimpleName()).log(Level.SEVERE, e);
    }

    /**
     *
     * @param action
     * @return
     */
    public Map<String, String> getParameters(String action) {
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("action", action);
        return stringStringMap;
    }

}
