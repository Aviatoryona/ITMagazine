package com.magazineapp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.magazineapp.main.BaseActivity;
import com.magazineapp.main.ParseJson;
import com.magazineapp.models.MessageModel;

import org.json.JSONException;

import java.util.List;
import java.util.Map;

import pub.devrel.easypermissions.EasyPermissions;

public class MakeAdvert extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    String[] perms = {Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = validateUser();
        setContentView(R.layout.activity_make_advert);

        initViews();
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(view -> finish());
        if (!EasyPermissions.hasPermissions(this, perms)) {
            EasyPermissions.requestPermissions(this, MakeAdvert.class.getSimpleName(), 200, perms);
        }
        txtPickImage.setOnClickListener(view -> selectImage(MakeAdvert.this));
        fab.setOnClickListener(view -> {
            try {
                makeAdvert();
            } catch (JSONException e) {
                showMessage("", "Please try again");
            }
        });

    }

    private void makeAdvert() throws JSONException {
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
            if (messageModel != null) {
                if (messageModel.isError()) {
                    showMessage("", messageModel.getMessage());
                } else {
                    showMessage("", "Advert successfully submitted");
                }
                return;
            }

            showMessage("Sorry", "Please try again");
        }, error -> {
            pg.dismiss();
            showMessage("Sorry", "Please try again");
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> jsonObject = getParameters("addadvert");
                jsonObject.put("title", edtTitle.getText().toString());
                jsonObject.put("desc", edtBody.getText().toString());
                jsonObject.put("user_id", String.valueOf(user.getId()));
                if (!base64Img.isEmpty())
                    jsonObject.put("img", base64Img);
                return jsonObject;
            }
        };
        App.getRequestQueue().add(stringRequest);
    }

    static String base64Img;

    private Toolbar toolbar;
    private EditText edtTitle;
    private EditText edtBody;
    private TextView txtPickImage;
    private ImageView img;
    private FloatingActionButton fab;

    public void initViews() {
        toolbar = findViewById(R.id.toolbar);
        edtTitle = findViewById(R.id.edtTitle);
        edtBody = findViewById(R.id.edtBody);
        txtPickImage = findViewById(R.id.txtPickImage);
        img = findViewById(R.id.img);
        fab = findViewById(R.id.fab);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK && data != null) {
                        Bitmap selectedImage = (Bitmap) data.getExtras().get("data");
                        img.setImageBitmap(selectedImage);
                        if (selectedImage != null)
                            base64Img = getEncoded64ImageStringFromBitmap(selectedImage);
                    }
                    break;

                case 1:
                    if (resultCode == RESULT_OK && data != null) {
                        Uri selectedImage = data.getData();
                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
                        if (selectedImage != null) {
                            Cursor cursor = getContentResolver().query(selectedImage,
                                    filePathColumn, null, null, null);
                            if (cursor != null) {
                                cursor.moveToFirst();

                                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                                String picturePath = cursor.getString(columnIndex);
                                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                                img.setImageBitmap(bitmap);
                                if (bitmap != null)
                                    base64Img = getEncoded64ImageStringFromBitmap(bitmap);
                                cursor.close();
                            }
                        }

                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(this, "Permission(s) granted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {

    }

    private void selectImage(Context context) {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose your profile picture");
        builder.setItems(options, (dialog, item) -> {
            if (options[item].equals("Take Photo")) {
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);

            } else if (options[item].equals("Choose from Gallery")) {
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);

            } else if (options[item].equals("Cancel")) {
                dialog.dismiss();
            }
        });
        builder.show();
    }
}