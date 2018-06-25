package com.codeforireland.vacanthome.utils;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.IOException;

public class PhotoFixOrirntation {


    private static final String TAG = PhotoFixOrirntation.class.getSimpleName();
    private final static int OPTIMIZED_WIDTH_MAX = 800;

    public static Bitmap corectPhoto(String filePath) throws IOException, OutOfMemoryError {
        ExifInterface exif = new ExifInterface(filePath);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);
        Matrix matrix = new Matrix();

        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                Log.d(TAG, "do nothing, ORIENTATION_NORMAL");
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                Log.d(TAG, "do nothing, ORIENTATION_UNKNOWN");
        }
        int newHight, newWidth;
        int scaleFactor = Math.max(bmOptions.outHeight/OPTIMIZED_WIDTH_MAX, bmOptions.outWidth/OPTIMIZED_WIDTH_MAX);
        Log.d(TAG, "scale factor: "+scaleFactor);
        if(bmOptions.outWidth> bmOptions.outHeight){
            newWidth = OPTIMIZED_WIDTH_MAX;
            newHight = bmOptions.outHeight/scaleFactor;
        }else {
            newHight = OPTIMIZED_WIDTH_MAX;
            newWidth = bmOptions.outWidth/scaleFactor;
        }
        Log.d(TAG, "original H: "+bmOptions.outHeight+" W: "+bmOptions.outWidth);
        Log.d(TAG, "scaled H: "+newHight+" W: "+newWidth);
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;
        return Bitmap.createBitmap(BitmapFactory.decodeFile(filePath, bmOptions), 0, 0, newWidth, newHight, matrix, true);
    }




}
