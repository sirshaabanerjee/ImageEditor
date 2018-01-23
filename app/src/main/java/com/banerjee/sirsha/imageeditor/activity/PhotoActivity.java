package com.banerjee.sirsha.imageeditor.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.adapter.EditOptionsAdapter;
import com.banerjee.sirsha.imageeditor.model.EditOptionModel;
import com.banerjee.sirsha.imageeditor.util.Constants;
import com.banerjee.sirsha.imageeditor.util.Utils;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.ArrayList;

public class PhotoActivity extends BaseActivity implements EditOptionsAdapter.EditOptionClick {

    private Button btnSave;
    private ImageView ivPreview;
    private RecyclerView rvEditOptions;

    private Uri imageUri;

    private ArrayList<EditOptionModel> arrEditOptionsList;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_photo;
    }

    @Override
    protected void initializeComponents() {
        btnSave = findViewById(R.id.activity_photo_btnSave);
        ivPreview = findViewById(R.id.activity_photo_ivPreview);
        rvEditOptions = findViewById(R.id.activity_photo_rvEditOptions);

        rvEditOptions.setHasFixedSize(true);
        rvEditOptions.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        btnSave.setOnClickListener(this);

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
        for (int i = 0; i < 10; i++) {
            final EditOptionModel editOptionModel = new EditOptionModel();
            editOptionModel.setEditOptionName("options");
            editOptionModel.setEditOptionImageResourceID(R.drawable.ic_placeholder);
            arrEditOptionsList.add(editOptionModel);
        }

        final EditOptionsAdapter editOptionsAdapter = new EditOptionsAdapter(arrEditOptionsList, PhotoActivity.this);
        rvEditOptions.setAdapter(editOptionsAdapter);
    }


    @Override
    public void onEditOptionClick(int position) {
        Toast.makeText(this, "position " + position, Toast.LENGTH_SHORT).show();
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
                final Intent intent = new Intent(PhotoActivity.this, EffectsActivity.class);
                intent.putExtra(Constants.BUNDLE_IMAGE_URI, String.valueOf(imageUri));
                startActivity(intent);
                break;
            case 2:
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

}
