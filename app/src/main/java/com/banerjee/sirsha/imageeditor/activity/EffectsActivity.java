package com.banerjee.sirsha.imageeditor.activity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.banerjee.sirsha.imageeditor.R;
import com.banerjee.sirsha.imageeditor.adapter.EffectsThumbnailAdapter;
import com.banerjee.sirsha.imageeditor.model.FilterThumbnailModel;
import com.banerjee.sirsha.imageeditor.util.Constants;
import com.banerjee.sirsha.imageeditor.util.FileUtils;
import com.banerjee.sirsha.imageeditor.util.Utils;
import com.mukesh.image_processing.ImageProcessor;

import java.util.ArrayList;

public class EffectsActivity extends BaseActivity implements EffectsThumbnailAdapter.EffectsThumbnailClick {

    private RecyclerView rvEffectsThumbnails;
    private ImageView ivPreview;
    private ProgressBar pbLoading;

    private ImageProcessor mImageProcessor;
    private Bitmap imageBitmap;
    private Bitmap imageBitmapAfterFilter;

    private ArrayList<FilterThumbnailModel> arrEffectsThumbnailList;

    @Override
    protected int defineLayoutResource() {
        return R.layout.activity_effects;
    }

    @Override
    protected void initializeComponents() {
        initialize();
//        loadBitmaps();
//        setAdapterAndListener();
        new AsyncImageCompress().execute();
    }

    private void initialize() {
        ivPreview = findViewById(R.id.activity_effects_ivPreview);
        rvEffectsThumbnails = findViewById(R.id.activity_effects_rvFilterThumbnails);
        pbLoading = findViewById(R.id.activity_effects_pbLoading);
        final TextView tvHeader = findViewById(R.id.header_photo_tvHeader);
        final TextView tvSave = findViewById(R.id.header_photo_tvSave);
        final ImageButton ibBack = findViewById(R.id.header_photo_ibBack);

        ibBack.setOnClickListener(this);
        tvSave.setOnClickListener(this);

        tvHeader.setText(getString(R.string.label_effects));

        rvEffectsThumbnails.setHasFixedSize(true);
        rvEffectsThumbnails.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        mImageProcessor = new ImageProcessor();
        arrEffectsThumbnailList = new ArrayList<>();
        final Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constants.BUNDLE_IMAGE_URI)) {
                final Uri imageUri = Uri.parse(intent.getStringExtra(Constants.BUNDLE_IMAGE_URI));
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
        imageBitmapAfterFilter = mImageProcessor.applyFleaEffect(imageBitmap);
        filterFlea.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterFlea);
        imageBitmapAfterFilter = null;

        //Black tone filter
        final FilterThumbnailModel filterBlackTone = new FilterThumbnailModel();
        filterBlackTone.setFilterName(getString(R.string.label_black_tone));
        imageBitmapAfterFilter = mImageProcessor.applyBlackFilter(imageBitmap);
        filterBlackTone.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterBlackTone);
        imageBitmapAfterFilter = null;

        //snow filter
        final FilterThumbnailModel filterSnow = new FilterThumbnailModel();
        filterSnow.setFilterName(getString(R.string.label_snow));
        imageBitmapAfterFilter = mImageProcessor.applySnowEffect(imageBitmap);
        filterSnow.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSnow);
        imageBitmapAfterFilter = null;

        // shading filter
//        final FilterThumbnailModel filterShading = new FilterThumbnailModel();
//        filterShading.setFilterName(getString(R.string.label_shading));
//        imageBitmapAfterFilter = mImageProcessor.applyShadingFilter(imageBitmap, R.color.colorPrimaryDark);
//        filterShading.setFilterBitmap(imageBitmapAfterFilter);
//        arrEffectsThumbnailList.add(filterShading);
//        imageBitmapAfterFilter = null;

        // saturation filter
        final FilterThumbnailModel filterSaturation = new FilterThumbnailModel();
        filterSaturation.setFilterName(getString(R.string.label_saturation));
        imageBitmapAfterFilter = mImageProcessor.applySaturationFilter(imageBitmap, 1);
        filterSaturation.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSaturation);
        imageBitmapAfterFilter = null;

        // hue filter
        final FilterThumbnailModel filterHue = new FilterThumbnailModel();
        filterHue.setFilterName(getString(R.string.label_hue));
        imageBitmapAfterFilter = mImageProcessor.applyHueFilter(imageBitmap, 1);
        filterHue.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterHue);
        imageBitmapAfterFilter = null;

        // sepia filter
        final FilterThumbnailModel filterSepia = new FilterThumbnailModel();
        filterSepia.setFilterName(getString(R.string.label_sepia));
        imageBitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
        filterSepia.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSepia);
        imageBitmapAfterFilter = null;

        // emboss filter
        final FilterThumbnailModel filterEmboss = new FilterThumbnailModel();
        filterEmboss.setFilterName(getString(R.string.label_emobss));
        imageBitmapAfterFilter = mImageProcessor.emboss(imageBitmap);
        filterEmboss.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterEmboss);
        imageBitmapAfterFilter = null;

        // engrave filter
        final FilterThumbnailModel filterEngrave = new FilterThumbnailModel();
        filterEngrave.setFilterName(getString(R.string.label_engrave));
        imageBitmapAfterFilter = mImageProcessor.engrave(imageBitmap);
        filterEngrave.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterEngrave);
        imageBitmapAfterFilter = null;

        // smooth filter
        final FilterThumbnailModel filterReflection = new FilterThumbnailModel();
        filterReflection.setFilterName(getString(R.string.label_smooth));
        imageBitmapAfterFilter = mImageProcessor.applyReflection(imageBitmap);
        filterReflection.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterReflection);
        imageBitmapAfterFilter = null;

        // grey scale filter
        final FilterThumbnailModel filterGreyScale = new FilterThumbnailModel();
        filterGreyScale.setFilterName(getString(R.string.label_grey_scale));
        imageBitmapAfterFilter = mImageProcessor.doGreyScale(imageBitmap);
        filterGreyScale.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterGreyScale);
        imageBitmapAfterFilter = null;

        // invert filter
        final FilterThumbnailModel filterInvert = new FilterThumbnailModel();
        filterInvert.setFilterName(getString(R.string.label_invert));
        imageBitmapAfterFilter = mImageProcessor.doInvert(imageBitmap);
        filterInvert.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterInvert);
        imageBitmapAfterFilter = null;

//        final EffectsThumbnailAdapter effectsThumbnailAdapter = new EffectsThumbnailAdapter(arrEffectsThumbnailList, EffectsActivity.this);
//        rvEffectsThumbnails.setAdapter(effectsThumbnailAdapter);
//        pbLoading.setVisibility(View.GONE);
    }

    @Override
    public void onEffectOptionCLick(int position) {
        switch (position) {
            case 0:
                imageBitmapAfterFilter = mImageProcessor.applyFleaEffect(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 1:
                imageBitmapAfterFilter = mImageProcessor.applyBlackFilter(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 2:
                imageBitmapAfterFilter = mImageProcessor.applySnowEffect(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 3:
                imageBitmapAfterFilter = mImageProcessor.applySaturationFilter(imageBitmap, 1);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 4:
                imageBitmapAfterFilter = mImageProcessor.applyHueFilter(imageBitmap, 1);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 5:
                imageBitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 6:
                imageBitmapAfterFilter = mImageProcessor.emboss(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 7:
                imageBitmapAfterFilter = mImageProcessor.engrave(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 8:
                imageBitmapAfterFilter = mImageProcessor.applyReflection(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 9:
                imageBitmapAfterFilter = mImageProcessor.doGreyScale(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            case 10:
                imageBitmapAfterFilter = mImageProcessor.doInvert(imageBitmap);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
//                imageBitmapAfterFilter = null;
                break;
            default:
                break;
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
                if(imageBitmapAfterFilter != null){
                    Utils.createDirectoryAndSaveFile(imageBitmapAfterFilter, getString(R.string.app_name), this);
                }
                break;
            default:
                break;
        }
    }

    public class AsyncImageCompress extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(String... strings) {
            loadBitmaps();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            final EffectsThumbnailAdapter effectsThumbnailAdapter = new EffectsThumbnailAdapter(arrEffectsThumbnailList, EffectsActivity.this);
            rvEffectsThumbnails.setAdapter(effectsThumbnailAdapter);
            pbLoading.setVisibility(View.GONE);
        }
    }

}
