package com.banerjee.sirsha.imageeditor.model;

import android.graphics.Bitmap;

/**
 * Created by Sirsha Banerjee on 22/1/18.
 */

public class FilterThumbnailModel {

    private String filterName;
    private Bitmap filterBitmap;

    public String getFilterName() {
        return filterName;
    }

    public void setFilterName(String filterName) {
        this.filterName = filterName;
    }

    public Bitmap getFilterBitmap() {
        return filterBitmap;
    }

    public void setFilterBitmap(Bitmap filterBitmap) {
        this.filterBitmap = filterBitmap;
    }
}
