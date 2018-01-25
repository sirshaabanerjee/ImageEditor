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
import com.banerjee.sirsha.imageeditor.adapter.FilterThumbnailAdapter;
import com.banerjee.sirsha.imageeditor.model.FilterThumbnailModel;
import com.banerjee.sirsha.imageeditor.util.Constants;
import com.banerjee.sirsha.imageeditor.util.FileUtils;
import com.banerjee.sirsha.imageeditor.util.Utils;
import com.mukesh.image_processing.ImageProcessingConstants;
import com.mukesh.image_processing.ImageProcessor;

import java.util.ArrayList;

public class FilterActivity extends BaseActivity implements FilterThumbnailAdapter.FilterThumbnailClick {

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

        tvHeader.setText(getString(R.string.label_filter));

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

        // 1
        final FilterThumbnailModel filterGamma = new FilterThumbnailModel();
        filterGamma.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.doGamma(imageBitmap, 1.8, 1.8, 1.8);
        filterGamma.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterGamma);
        imageBitmapAfterFilter = null;

        //2
        final FilterThumbnailModel filterColor1 = new FilterThumbnailModel();
        filterColor1.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.doColorFilter(imageBitmap, 1, 0, 0);
        filterColor1.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterColor1);
        imageBitmapAfterFilter = null;

        // 3
        final FilterThumbnailModel filterSepia = new FilterThumbnailModel();
        filterSepia.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
        filterSepia.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSepia);
        imageBitmapAfterFilter = null;

        // 4
        final FilterThumbnailModel filterBoost = new FilterThumbnailModel();
        filterBoost.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.boost(imageBitmap, ImageProcessingConstants.GREEN, 0.5);
        filterBoost.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterBoost);
        imageBitmapAfterFilter = null;

        // 5
        final FilterThumbnailModel filterSaturation = new FilterThumbnailModel();
        filterSaturation.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.applySaturationFilter(imageBitmap, 5);
        filterSaturation.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSaturation);
        imageBitmapAfterFilter = null;

        // 6
        final FilterThumbnailModel filterColor2 = new FilterThumbnailModel();
        filterColor2.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.doColorFilter(imageBitmap, 0, 0, 1);
        filterColor2.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterColor2);
        imageBitmapAfterFilter = null;

        // 7
        final FilterThumbnailModel filterContrast = new FilterThumbnailModel();
        filterContrast.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.createContrast(imageBitmap, 50);
        filterContrast.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterContrast);
        imageBitmapAfterFilter = null;

        // 8
        final FilterThumbnailModel filterSepiaToning = new FilterThumbnailModel();
        filterSepiaToning.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
        filterSepiaToning.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterSepiaToning);
        imageBitmapAfterFilter = null;

        // 9
        final FilterThumbnailModel filterHue = new FilterThumbnailModel();
        filterHue.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.applyHueFilter(imageBitmap, 5);
        filterHue.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterHue);
        imageBitmapAfterFilter = null;

        // 10
        final FilterThumbnailModel filterBright = new FilterThumbnailModel();
        filterBright.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.doBrightness(imageBitmap, 30);
        filterBright.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterBright);
        imageBitmapAfterFilter = null;

        // 11
        final FilterThumbnailModel filterBoost3 = new FilterThumbnailModel();
        filterBoost3.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.boost(imageBitmap, ImageProcessingConstants.BLUE, 0.67);
        filterBoost3.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterBoost3);
        imageBitmapAfterFilter = null;

        // 12
        final FilterThumbnailModel filterBoost4 = new FilterThumbnailModel();
        filterBoost4.setFilterName(getString(R.string.label_filter));
        imageBitmapAfterFilter = mImageProcessor.boost(imageBitmap, ImageProcessingConstants.RED, 1.5);
        filterBoost4.setFilterBitmap(imageBitmapAfterFilter);
        arrEffectsThumbnailList.add(filterBoost4);
        imageBitmapAfterFilter = null;

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
            final FilterThumbnailAdapter filterThumbnailAdapter = new FilterThumbnailAdapter(arrEffectsThumbnailList, FilterActivity.this);
            rvEffectsThumbnails.setAdapter(filterThumbnailAdapter);
            pbLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public void onFilterOptionCLick(int position) {
        switch (position) {
            case 0:
                imageBitmapAfterFilter = mImageProcessor.doGamma(imageBitmap, 1.8, 1.8, 1.8);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 1:
                imageBitmapAfterFilter = mImageProcessor.doColorFilter(imageBitmap, 1, 0, 0);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 2:
                imageBitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 3:
                imageBitmapAfterFilter = mImageProcessor.boost(imageBitmap, ImageProcessingConstants.GREEN, 0.5);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 4:
                imageBitmapAfterFilter = mImageProcessor.applySaturationFilter(imageBitmap, 5);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 5:
                imageBitmapAfterFilter = mImageProcessor.doColorFilter(imageBitmap, 0, 0, 1);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 6:
                imageBitmapAfterFilter = mImageProcessor.createContrast(imageBitmap, 50);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 7:
                imageBitmapAfterFilter = mImageProcessor.createSepiaToningEffect(imageBitmap, 150, 0.12, 0.7, 0.3);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 8:
                imageBitmapAfterFilter = mImageProcessor.applyHueFilter(imageBitmap, 5);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 9:
                imageBitmapAfterFilter = mImageProcessor.doBrightness(imageBitmap, 30);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 10:
                imageBitmapAfterFilter = mImageProcessor.boost(imageBitmap, ImageProcessingConstants.BLUE, 0.67);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
                break;
            case 11:
                imageBitmapAfterFilter = mImageProcessor.boost(imageBitmap, ImageProcessingConstants.RED, 1.5);
                ivPreview.setImageBitmap(imageBitmapAfterFilter);
                imageBitmapAfterFilter = null;
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

}
