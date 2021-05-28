package com.sai.eventsports.splash_login_register;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.sai.eventsports.CollectData;
import com.sai.eventsports.R;
import com.sai.eventsports.entidades.User;
import com.sai.eventsports.Util;
import com.sai.eventsports.principales.ActivityMain;

import java.util.Objects;

public class ActivityRegisterAlert extends AppCompatActivity {

    private static final int REQUEST_PERMISSON_CODE = 100;
    private static final int REQUEST_IMAGE_GALLERY = 101;
    private Uri imageUri;
    private ImageView galleryImage;
    private Button btnRegister;
    private TextInputLayout tlf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_alert);

        galleryImage = (ImageView) findViewById(R.id.perfilAlert_iv);
        btnRegister = findViewById(R.id.btn_register_alert);
        tlf = findViewById(R.id.textInputPhoneNumberLogin);

        Glide.with(this)
                .load("android.resource://" + getPackageName() + "/" + R.drawable.user_profile)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .circleCrop()
                .into(galleryImage);

        galleryImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(ActivityRegisterAlert.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        openGallery();
                    } else {
                        ActivityCompat.requestPermissions(ActivityRegisterAlert.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSON_CODE);
                    }
                }

            }
        });
        Intent intent = getIntent();
        String email = intent.getStringExtra("userGoogle");
        String userName = intent.getStringExtra("userName");
        if (userName == null){
            userName = Util.chageName(email);
        }
        String finalUserName = userName;
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = new User(ActivityLogIn.USERUID, finalUserName,email, Objects.requireNonNull(tlf.getEditText()).getText().toString().trim());
                CollectData.saveUser(u);
                Intent accessIntent = new Intent(getApplicationContext(), ActivityMain.class);
                accessIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                accessIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(accessIntent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_GALLERY) {
            imageUri = data.getData();

            Glide.with(this)
                    .load(imageUri)
                    .centerCrop()
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .circleCrop()
                    .into(galleryImage);
        } else {
            Log.i("RAG", "Result: " + resultCode);
            Toast.makeText(this, "You did not choose any photo", Toast.LENGTH_SHORT).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSON_CODE) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                Toast.makeText(this, "You need to enable permissions", Toast.LENGTH_SHORT).show();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
    }



}