package com.codeforireland.vacanthome.viewmodels;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.graphics.Bitmap;


public class PhotoViewModel  extends ViewModel{

    private final static String TAG = PhotoViewModel.class.getSimpleName();

    private MutableLiveData<Bitmap> mutableLiveData;

    public LiveData<Bitmap> getPicture(Activity activity){//or just pass intent???
        if(mutableLiveData==null){
            mutableLiveData = new MutableLiveData<>();
            takePicture();
        }
        return mutableLiveData;
    }

    private void takePicture() {
        //call implicit intent to camera app
    }


}
