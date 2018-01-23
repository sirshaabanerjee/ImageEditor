package com.banerjee.sirsha.imageeditor.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * Created by Sirsha Banerjee on 18/1/18.
 */

public class PermissionUtils {
    public static final String ACCESS_CAMERA_PERMISSION = Manifest.permission.CAMERA;
    public static final String ACCESS_READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String ACCESS_WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public static final int REQUEST_CAMERA = 0;
    public static final int REQUEST_READ_STORAGE = 1;

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    /*
    Declaring all permissions of one group
     */


    private Activity mActivity;
    private Fragment mFragment;

    public PermissionUtils(Activity activity) {

        this.mActivity = activity;
    }

    //    /**
//     * Constructor for accessing permissions from Fragment.
//     *
//     * @param fragment requesting fragment to recieve callback in same fragment
//     * @param activity parent activity
//     */
    public PermissionUtils(Fragment fragment, Activity activity) {

        this.mFragment = fragment;
        this.mActivity = activity;
    }

    /**
     * Called to check permission(In Android M and above versions only)
     *
     * @param permission, which we need to pass
     * @return true, if permission is granted else false
     */
    public static boolean checkForPermission(final Context context, final String permission) {
        int result = ContextCompat.checkSelfPermission(context, permission);
        //If permission is granted then it returns 0 as result
        return result == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Method to request Camera permissions
     */
    public boolean getCameraPermission() {
        //Managing run time permission for camera and external storage .
        if (PermissionUtils.checkForPermission(mActivity, PermissionUtils.ACCESS_CAMERA_PERMISSION) && PermissionUtils.checkForPermission(mActivity,
                PermissionUtils.ACCESS_READ_EXTERNAL_STORAGE)) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (mFragment != null) {
                    mFragment.requestPermissions(new String[]{PermissionUtils.ACCESS_CAMERA_PERMISSION, PermissionUtils.ACCESS_READ_EXTERNAL_STORAGE,
                            PermissionUtils.ACCESS_WRITE_EXTERNAL_STORAGE}, Constants.PERMISSION_REQUEST_ACCESS_CAMERA_CODE);
                }
            }
            return false;
        }
    }




    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context)
    {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if(currentAPIVersion>=android.os.Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)
                        && ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA },
                                    Constants.PERMISSION_REQUEST_ACCESS_CAMERA_CODE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();

                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA}, Constants.PERMISSION_REQUEST_ACCESS_CAMERA_CODE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

}
