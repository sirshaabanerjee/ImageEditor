package com.banerjee.sirsha.imageeditor.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.adapter.EditOptionsAdapter;
import com.banerjee.sirsha.imageeditor.model.EditOptionModel;
import com.banerjee.sirsha.imageeditor.util.Constants;
import com.banerjee.sirsha.imageeditor.util.Utils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class PhotoActivity extends BaseActivity implements EditOptionsAdapter.EditOptionClick {

    private ImageView ivPreview;
    private RecyclerView rvEditOptions;

    private Uri imageUri;

    private Intent intent;

    private ArrayList<EditOptionModel> arrEditOptionsList;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initializeComponents() {
        ivPreview = findViewById(R.id.activity_photo_ivPreview);
        rvEditOptions = findViewById(R.id.activity_photo_rvEditOptions);
        final TextView tvHeader = findViewById(R.id.header_photo_tvHeader);
        final TextView tvSave = findViewById(R.id.header_photo_tvSave);
        final ImageButton ibBack = findViewById(R.id.header_photo_ibBack);

        ibBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        tvHeader.setText(getString(R.string.label_filter));

        rvEditOptions.setHasFixedSize(true);
        rvEditOptions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));


        final Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constants.BUNDLE_IMAGE_URI)) {
                imageUri = Uri.parse(intent.getStringExtra(Constants.BUNDLE_IMAGE_URI));
                Utils.setImageView(this, String.valueOf(imageUri), ivPreview, R.drawable.ic_placeholder, false);
            }
        }

        arrEditOptionsList = new ArrayList<>();
        createEditOptions();
    }

    // method creates a list of options on the recycler view
    private void createEditOptions() {
        final EditOptionModel cropOption = new EditOptionModel();
        cropOption.setEditOptionName(getString(R.string.label_crop));
        cropOption.setEditOptionImageResourceID(R.drawable.ic_crop);
        arrEditOptionsList.add(cropOption);

        final EditOptionModel effectsOption = new EditOptionModel();
        effectsOption.setEditOptionName(getString(R.string.label_effects));
        effectsOption.setEditOptionImageResourceID(R.drawable.ic_effects);
        arrEditOptionsList.add(effectsOption);

        final EditOptionModel filterOption = new EditOptionModel();
        filterOption.setEditOptionName(getString(R.string.label_filter));
        filterOption.setEditOptionImageResourceID(R.drawable.ic_filter);
        arrEditOptionsList.add(filterOption);


        final EditOptionsAdapter editOptionsAdapter = new EditOptionsAdapter(arrEditOptionsList, PhotoActivity.this);
        rvEditOptions.setAdapter(editOptionsAdapter);
    }


    @Override
    public void onEditOptionClick(int position) {
//        final Intent intent = new Intent(PhotoActivity.this, EditActivity.class);
//        intent.putExtra(Constants.BUNDLE_IMAGE_URI, imagePath);
//        startActivity(intent);
        switch (position) {
            case 0:
                // start picker to get image for cropping and then use the image in cropping activity
                // start cropping activity for pre-acquired image saved on the device
                CropImage.activity(imageUri)
                        .start(this);
                break;
            case 1:
                intent = new Intent(PhotoActivity.this, EffectsActivity.class);
                intent.putExtra(Constants.BUNDLE_IMAGE_URI, String.valueOf(imageUri));
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(PhotoActivity.this, FilterActivity.class);
                intent.putExtra(Constants.BUNDLE_IMAGE_URI, String.valueOf(imageUri));
                startActivity(intent);
                break;
            case 3:
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                ivPreview.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.header_photo_ibBack:
                finish();
                break;
            case R.id.header_photo_tvSave:
                break;
            default:
                break;
        }
    }

}
