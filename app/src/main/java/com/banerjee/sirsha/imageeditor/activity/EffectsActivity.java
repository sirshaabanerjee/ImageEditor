package com.banerjee.sirsha.imageeditor.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.Toast;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.adapter.EffectsThumbnailAdapter;
import com.banerjee.sirsha.imageeditor.model.FilterThumbnailModel;
import com.banerjee.sirsha.imageeditor.util.Constants;
import com.banerjee.sirsha.imageeditor.util.FileUtils;
import com.banerjee.sirsha.imageeditor.util.Utils;
import com.mukesh.image_processing.ImageProcessor;

import java.io.IOException;
import java.util.ArrayList;

public class EffectsActivity extends BaseActivity implements EffectsThumbnailAdapter.EffectsThumbnailClick {

    private RecyclerView rvEffectsThumbnails;
    private ImageView ivPreview;

    private ImageProcessor mImageProcessor;
    private Uri imageUri;
    private Bitmap imageBitmap;
    private Bitmap imagebitmapAfterFilter;

    private ArrayList<FilterThumbnailModel> arrEffectsThumbnailList;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_effects;
    }

    @Override
    protected void initializeComponents() {
        initialize();
        loadBitmaps();
//        setAdapterAndListener();
    }

    private void initialize() {
        ivPreview = findViewById(R.id.activity_effects_ivPreview);
        rvEffectsThumbnails = findViewById(R.id.activity_effects_rvFilterThumbnails);

        rvEffectsThumbnails.setHasFixedSize(true);
        rvEffectsThumbnails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mImageProcessor = new ImageProcessor();
        arrEffectsThumbnailList = new ArrayList<>();
        final Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constants.BUNDLE_IMAGE_URI)) {
                imageUri = Uri.parse(intent.getStringExtra(Constants.BUNDLE_IMAGE_URI));
                imageBitmap = FileUtils.getBitmapFromUri(this, imageUri, 360);
//                Utils.setImageView(this, String.valueOf(imageUri), ivPreview, R.drawable.ic_placeholder, false);
                ivPreview.setImageBitmap(imageBitmap);
            }
        }
    }

    private void loadBitmaps() {

        // flea filter
        final FilterThumbnailModel filterFlea = new FilterThumbnailModel();
        filterFlea.setFilterName(getString(R.string.label_Flea));
        imagebitmapAfterFilter = mImageProcessor.applyFleaEffect(imageBitmap);
        filterFlea.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterFlea);
        imagebitmapAfterFilter = null;

        //Black tone filter
        final FilterThumbnailModel filterBlackTone = new FilterThumbnailModel();
        filterBlackTone.setFilterName(getString(R.string.label_black_tone));
        imagebitmapAfterFilter = mImageProcessor.applyBlackFilter(imageBitmap);
        filterBlackTone.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterBlackTone);
        imagebitmapAfterFilter = null;

        //snow filter
        final FilterThumbnailModel filterSnow = new FilterThumbnailModel();
        filterSnow.setFilterName(getString(R.string.label_snow));
        imagebitmapAfterFilter = mImageProcessor.applySnowEffect(imageBitmap);
        filterSnow.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSnow);
        imagebitmapAfterFilter = null;

        // shading filter
        final FilterThumbnailModel filterShading = new FilterThumbnailModel();
        filterShading.setFilterName(getString(R.string.label_shading));
        imagebitmapAfterFilter = mImageProcessor.applyShadingFilter(imageBitmap, R.color.colorPrimaryDark);
        filterShading.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterShading);
        imagebitmapAfterFilter = null;

        // saturation filter
        final FilterThumbnailModel filterSaturation = new FilterThumbnailModel();
        filterSaturation.setFilterName(getString(R.string.label_saturation));
        imagebitmapAfterFilter = mImageProcessor.applySaturationFilter(imageBitmap, 1);
        filterSaturation.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSaturation);
        imagebitmapAfterFilter = null;

        // hue filter
        final FilterThumbnailModel filterHue = new FilterThumbnailModel();
        filterHue.setFilterName(getString(R.string.label_hue));
        imagebitmapAfterFilter = mImageProcessor.applyHueFilter(imageBitmap, 1);
        filterHue.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterHue);
        imagebitmapAfterFilter = null;

        // sepia filter
        final FilterThumbnailModel filterSepia = new FilterThumbnailModel();
        filterSepia.setFilterName(getString(R.string.label_sepia));
        imagebitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
        filterSepia.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSepia);
        imagebitmapAfterFilter = null;

        // emboss filter
        final FilterThumbnailModel filterEmboss = new FilterThumbnailModel();
        filterEmboss.setFilterName(getString(R.string.label_emobss));
        imagebitmapAfterFilter = mImageProcessor.emboss(imageBitmap);
        filterEmboss.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterEmboss);
        imagebitmapAfterFilter = null;

        // engrave filter
        final FilterThumbnailModel filterEngrave = new FilterThumbnailModel();
        filterEngrave.setFilterName(getString(R.string.label_engrave));
        imagebitmapAfterFilter = mImageProcessor.engrave(imageBitmap);
        filterEngrave.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterEngrave);
        imagebitmapAfterFilter = null;

        // smooth filter
        final FilterThumbnailModel filterSmooth = new FilterThumbnailModel();
        filterSmooth.setFilterName(getString(R.string.label_smooth));
        imagebitmapAfterFilter = mImageProcessor.smooth(imageBitmap, 100);
        filterSmooth.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSmooth);
        imagebitmapAfterFilter = null;

        // grey scale filter
        final FilterThumbnailModel filterGreyScale = new FilterThumbnailModel();
        filterGreyScale.setFilterName(getString(R.string.label_grey_scale));
        imagebitmapAfterFilter = mImageProcessor.doGreyScale(imageBitmap);
        filterGreyScale.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterGreyScale);
        imagebitmapAfterFilter = null;

        // invert filter
        final FilterThumbnailModel filterInvert = new FilterThumbnailModel();
        filterInvert.setFilterName(getString(R.string.label_invert));
        imagebitmapAfterFilter = mImageProcessor.doInvert(imageBitmap);
        filterInvert.setFilterBitmap(imagebitmapAfterFilter);
        arrEffectsThumbnailList.add(filterInvert);
        imagebitmapAfterFilter = null;

        final EffectsThumbnailAdapter effectsThumbnailAdapter = new EffectsThumbnailAdapter(arrEffectsThumbnailList, EffectsActivity.this);
        rvEffectsThumbnails.setAdapter(effectsThumbnailAdapter);
    }

    @Override
    public void onEffectOptionCLick(int position) {
        switch (position) {
            case 0:
                Toast.makeText(this, "pos" + position, Toast.LENGTH_SHORT).show();
                imagebitmapAfterFilter = mImageProcessor.applyFleaEffect(imageBitmap);
                ivPreview.setImageBitmap(imagebitmapAfterFilter);
                imagebitmapAfterFilter = null;
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
            case 8:
                break;
            case 9:
                break;
            case 10:
                break;
            case 11:
                break;
            default:
                break;
        }
    }
}
