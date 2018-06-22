package com.codeforireland.vacanthome.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;
import android.view.Display;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PhotoFixOrirntation {


    private static final String TAG = PhotoFixOrirntation.class.getSimpleName();


    private final static int OPTIMIZED_WIDTH_MAX = 800;

    public static Bitmap corectPhoto(String fileName, Uri selectedImage, ContentResolver cr) throws IOException, OutOfMemoryError {
        Log.d(TAG, "file path: "+fileName);
        Log.d(TAG, "uri path: "+selectedImage.getPath());
        ExifInterface exif = new ExifInterface(fileName);
        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        Matrix matrix = new Matrix();

        //TODO: delete Logs
        switch (orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
                Log.d(TAG, " orientation :::: ORIENTATION_NORMAL");
                return android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                Log.d(TAG, " orientation :::: ORIENTATION_FLIP_HORIZONTAL");
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                Log.d(TAG, " orientation :::: ORIENTATION_ROTATE_180");
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                Log.d(TAG, " orientation :::: ORIENTATION_FLIP_VERTICAL");
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                Log.d(TAG, " orientation :::: ORIENTATION_TRANSPOSE");
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                Log.d(TAG, " orientation :::: ORIENTATION_ROTATE_90");
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                Log.d(TAG, " orientation :::: ORIENTATION_TRANSVERSE");
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                Log.d(TAG, " orientation :::: ORIENTATION_ROTATE_270");
                matrix.setRotate(-90);
                break;
            default:
                Log.d(TAG, " orientation :::: ORIENTATION_UNDEFINED");
                return android.provider.MediaStore.Images.Media.getBitmap(cr, selectedImage);
        }
        Bitmap bitmap = android.provider.MediaStore.Images.Media
                .getBitmap(cr, selectedImage);
        Log.d(TAG, "decoded Bitmap H: "+bitmap.getHeight()+" W: "+bitmap.getWidth());
        Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle();
        Log.d(TAG, "rotated Bitmap H: "+bmRotated.getHeight()+" W: "+bmRotated.getWidth());
        return bmRotated;
    }




}
