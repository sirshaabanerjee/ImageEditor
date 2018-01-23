package com.banerjee.sirsha.imageeditor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.util.Constants;
import com.banerjee.sirsha.imageeditor.util.FileUtils;
import com.banerjee.sirsha.imageeditor.util.PermissionUtils;
import com.banerjee.sirsha.imageeditor.util.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class HomeActivity extends BaseActivity {

    private Button btnTakePhoto;
    private Button btnSelectFromGallery;

    private File imageFile;
    private boolean isCamera; // boolean determines what intent user is trying to open camera or gallery

    private Uri imageUri;

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

    /**
     * method opens the device's camera
     */
    private void cameraIntent() {

        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, Constants.REQUEST_CODE_CAMERA);
    }

    /**
     * method opens the device's photo gallery
     */
    private void galleryIntent() {

        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_file)), Constants.REQUEST_CODE_GALLERY);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case Constants.PERMISSION_REQUEST_ACCESS_CAMERA_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                        ) {
                    if (isCamera)
                        cameraIntent();
                    else
                        galleryIntent();
                } else {
                    Toast.makeText(this, "Please grant permissions", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.REQUEST_CODE_GALLERY) {
                imageFile = FileUtils.getFile(this, data.getData());
                imageUri = FileUtils.getUri(imageFile);
//                imageUri = Uri.parse(imageFile.getAbsolutePath());
            } else if (requestCode == Constants.REQUEST_CODE_CAMERA) {

                onCaptureImageResult(data);

            }
        }

        // move to next page
        final Intent intent = new Intent(HomeActivity.this, PhotoActivity.class);
        intent.putExtra(Constants.BUNDLE_IMAGE_URI, String.valueOf(imageUri));
        startActivity(intent);
    }

    /**
     * method will be called after user has captured an image through camera
     * image will be stored as bitmap in a filed named as name of the application
     *
     * @param data
     */
    private void onCaptureImageResult(Intent data) {

        final Bitmap thumbnail = (Bitmap) data.getExtras().get(getString(R.string.label_bitmap_data));
        final ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        if (thumbnail != null) {
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        }
        imageFile = Utils.createDirectoryAndSaveFile(thumbnail, getString(R.string.app_name), this);
        if (imageFile != null) {
            imageUri = Uri.fromFile(imageFile);
        }
//        Utils.setImageView(this, uri.toString(), ivProfileImage, R.drawable.ic_profile, true);
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {

            case R.id.activity_home_btnTakePhoto:
                isCamera = true;
                if (PermissionUtils.checkPermission(this)) {
                    cameraIntent();
                }
                break;
            case R.id.activity_home_btnSelectFromGallery:
                isCamera = false;
                if (PermissionUtils.checkPermission(this)) {
                    galleryIntent();
                }
                break;
            default:
                break;

        }
    }
}
