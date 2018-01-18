package com.banerjee.sirsha.imageeditor.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.banerjee.sirsha.imageeditor.R;

public class HomeActivity extends BaseActivity{

    private Button btnTakePhoto;
    private Button btnSelectFromGallery;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_home;
    }

    @Override
    protected void initializeComponents() {
        btnTakePhoto = findViewById(R.id.activity_home_btnTakePhoto);
        btnSelectFromGallery = findViewById(R.id.activity_home_btnSelectFromGallery);

        btnTakePhoto.setOnClickListener(this);
        btnSelectFromGallery.setOnClickListener(this);
    }
}
