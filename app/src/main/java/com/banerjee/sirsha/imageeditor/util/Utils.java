package com.banerjee.sirsha.imageeditor.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;

import com.banerjee.sirsha.imageeditor.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by Sirsha Banerjee on 18/1/18.
 */

public class Utils {

    /**
     * method creates a directory named as application name
     * checks if directory already exists then ignores
     * inside directory saves the image that is taken by the user in jpeg format and names it after the current time millisecond
     * returns image uri i.e the path of the image in the external storage.
     *
     * @param imageToSave
     * @param imageName
     * @return
     */
    public static File createDirectoryAndSaveFile(Bitmap imageToSave, String imageName, final Context context) {

        final File direct = new File(Environment.getExternalStorageDirectory() + context.getString(R.string.label_sdcard_file_name));

        if (!direct.exists()) {
            final File newFile = new File(Environment.getExternalStorageDirectory() + context.getString(R.string.label_sdcard_file_name));
            newFile.mkdirs();
        }

        final File file = new File(Environment.getExternalStorageDirectory() + context.getString(R.string.label_sdcard_file_name), imageName +
                System.currentTimeMillis() + context.getString(R.string.label_image_type));
        if (file.exists()) {
            file.delete();
        }
        try {
            final FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    /**
     * To load image in image view. Set parameters according to requirement.
     *
     * @param context
     * @param imageUrl
     * @param imageView
     */
    public static void setImageView(final Context context, final String imageUrl, final ImageView imageView, final int placeHolder, final Boolean isRoundImage) {

        if (imageUrl != null && imageView != null && !imageUrl.equals("")) {
            Glide.clear(imageView);
            Glide.with(context)
                    .load(imageUrl)
                    .override(1075, 1075)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .centerCrop()
                    .placeholder(placeHolder)
                    .dontAnimate()
                    .into(new GlideDrawableImageViewTarget(imageView) {

                        @Override
                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {

                            super.onResourceReady(resource, animation);
                            Bitmap source = ((GlideBitmapDrawable) imageView.getDrawable().getCurrent()).getBitmap();

                            if (isRoundImage) {
                                RoundedBitmapDrawable drawable = RoundedBitmapDrawableFactory.create(context.getResources(), source);
                                drawable.setCircular(true);
                                imageView.setImageDrawable(drawable);
                            } else {
                                imageView.setImageBitmap(source);
                            }

                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {

                            super.onLoadFailed(e, errorDrawable);
                            //never called
                        }
                    });
        }

    }

    /**
     * Checks the Network availability.
     *
     * @return isNetAvailable: true id internet available, false otherwise
     */

    @SuppressWarnings("deprecation")
    public static boolean isNetworkAvailable(Context context) {

        boolean isNetAvailable = false;
        if (context != null) {
            final ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (mConnectivityManager != null) {

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    final NetworkInfo activeNetwork = mConnectivityManager.getActiveNetworkInfo();
                    if (activeNetwork != null) {
                        isNetAvailable = true;
                    }

                } else {
                    boolean wifiNetworkConnected = false;
                    boolean mobileNetworkConnected = false;

                    final NetworkInfo mobileInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                    final NetworkInfo wifiInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

                    if (mobileInfo != null) {
                        mobileNetworkConnected = mobileInfo.isConnected();
                    }
                    if (wifiInfo != null) {
                        wifiNetworkConnected = wifiInfo.isConnected();
                    }
                    isNetAvailable = (mobileNetworkConnected || wifiNetworkConnected);
                }
            }
        }
        return isNetAvailable;
    }



}
